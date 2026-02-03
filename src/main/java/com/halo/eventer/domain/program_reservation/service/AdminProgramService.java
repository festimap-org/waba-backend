package com.halo.eventer.domain.program_reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import com.halo.eventer.domain.program_reservation.dto.request.*;
import com.halo.eventer.domain.program_reservation.dto.response.ProgramActiveResponse;
import com.halo.eventer.domain.program_reservation.dto.response.ProgramBookingResponse;
import com.halo.eventer.domain.program_reservation.dto.response.ProgramDetailResponse;
import com.halo.eventer.domain.program_reservation.dto.response.ProgramResponse;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.program_reservation.FestivalCommonTemplate;
import com.halo.eventer.domain.program_reservation.Program;
import com.halo.eventer.domain.program_reservation.ProgramBlock;
import com.halo.eventer.domain.program_reservation.ProgramTag;
import com.halo.eventer.domain.program_reservation.Tag;
import com.halo.eventer.domain.program_reservation.repository.ProgramBlockRepository;
import com.halo.eventer.domain.program_reservation.repository.ProgramRepository;
import com.halo.eventer.domain.program_reservation.repository.ProgramTagRepository;
import com.halo.eventer.domain.program_reservation.repository.TagRepository;
import com.halo.eventer.domain.program_reservation.repository.FestivalCommonTemplateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProgramService {
    private final ProgramRepository programRepository;
    private final ProgramBlockRepository programBlockRepository;
    private final ProgramTagRepository programTagRepository;
    private final TagRepository tagRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalCommonTemplateRepository templateRepository;

    @Transactional
    public void create(Long festivalId, ProgramCreateRequest request) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(() -> new BaseException("존재하지 않는 축제입니다.", ErrorCode.ENTITY_NOT_FOUND));
        Program program = new Program(request.getName());
        program.assignFestival(festival);
        programRepository.save(program);
    }

    @Transactional
    public void rename(Long programId, ProgramRenameRequest request) {
        Program program = loadProgramOrThrow(programId);
        program.updateName(request.getName());
    }

    @Transactional
    public void delete(Long programId) {
        Program program = loadProgramOrThrow(programId);
        programTagRepository.deleteAllByProgramId(programId);
        programBlockRepository.deleteAllByProgramId(programId);
        programRepository.delete(program);
    }

    @Transactional(readOnly = true)
    public List<ProgramResponse> getList(Long festivalId, String name) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(() -> new BaseException("존재하지 않는 축제입니다.", ErrorCode.ENTITY_NOT_FOUND));

        List<Program> programs;
        if (name != null && !name.isBlank()) {
            programs = programRepository.findAllByFestivalIdAndNameContaining(festivalId, name);
        } else {
            programs = programRepository.findAllByFestivalId(festivalId);
        }
        return programs.stream().map(ProgramResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProgramDetailResponse getDetail(Long programId) {
        Program program = loadProgramOrThrow(programId);
        List<ProgramTag> tags = programTagRepository.findAllByProgramIdOrderBySortOrder(programId);
        List<ProgramBlock> blocks = programBlockRepository.findAllByProgramIdOrderBySortOrder(programId);
        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(program.getFestival().getId());
        return ProgramDetailResponse.from(program, tags, blocks, templates);
    }

    @Transactional
    public void updateDetail(Long programId, ProgramUpdateRequest request) {
        Program program = loadProgramOrThrow(programId);

        program.update(
                request.getThumbnailUrl(),
                request.getPricingType(),
                request.getPriceAmount(),
                request.getDurationTime(),
                request.getAvailableAge(),
                request.getPersonLimit(),
                request.getMaxPersonCount()
        );

        // 태그 전체 교체
        programTagRepository.deleteAllByProgramId(programId);
        programTagRepository.flush();
        if (request.getTags() != null) {
            List<ProgramUpdateRequest.TagRequest> tags = request.getTags();
            for (int i = 0; i < tags.size(); i++) {
                Tag tag = tagRepository.findById(tags.get(i).getTagId()).orElseThrow(() -> new BaseException("존재하지 않는 태그입니다.", ErrorCode.ENTITY_NOT_FOUND));
                programTagRepository.save(ProgramTag.of(program, tag, i));
            }
        }

        // 블록 전체 교체
        programBlockRepository.deleteAllByProgramId(programId);
        programBlockRepository.flush(); // unique(program_id, type, sort_order) 충돌 방지: 기존 행을 먼저 DB에서 제거
        if (request.getBlocks() != null) {
            List<ProgramUpdateRequest.BlockRequest> blocks = request.getBlocks();
            for (int i = 0; i < blocks.size(); i++) {
                ProgramBlock block = createBlock(program, blocks.get(i), i);
                programBlockRepository.save(block);
            }
        }
    }

    private ProgramBlock createBlock(Program program, ProgramUpdateRequest.BlockRequest req, int sortOrder) {
        switch (req.getType()) {
            case SUMMARY:
                return ProgramBlock.summary(program, sortOrder, req.getSummaryLabel(), req.getSummaryValue());
            case DESCRIPTION:
                return ProgramBlock.description(program, sortOrder,
                        req.getDescriptionOneLine(), req.getDescriptionDetail(), req.getDescriptionImageUrl());
            case CAUTION:
                return ProgramBlock.caution(program, sortOrder, req.getCautionContent());
            default:
                throw new BaseException("존재하지 않는 블록 타입입니다.", ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @Transactional
    public void toggleActive(Long programId) {
        Program program = loadProgramOrThrow(programId);
        program.toggleActive();
    }

    @Transactional
    public void updateActiveInfo(Long programId, ProgramActiveInfoRequest request) {
        Program program = loadProgramOrThrow(programId);

        var start = java.time.LocalDateTime.of(request.getActiveStartDate(), request.getActiveStartTime());
        var end = java.time.LocalDateTime.of(request.getActiveEndDate(), request.getActiveEndTime());
        if (end.isBefore(start)) {
            throw new BaseException("노출 종료 시각은 시작 시각 이후여야 합니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        program.updateActiveStartAt(start);
        program.updateActiveEndAt(end);
    }

    @Transactional(readOnly = true)
    public ProgramActiveResponse getActiveInfo(Long programId) {
        Program program = loadProgramOrThrow(programId);
        return toActiveResponse(program);
    }

    private Program loadProgramOrThrow(Long programId) {
        return programRepository.findById(programId).orElseThrow(() -> new BaseException("존재하지 않는 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND));
    }

    private ProgramActiveResponse toActiveResponse(Program program) {
        return ProgramActiveResponse.of(
                program.getId(),
                program.isActive(),
                program.getActiveStartAt() != null ? program.getActiveStartAt().toLocalDate() : null,
                program.getActiveStartAt() != null ? program.getActiveStartAt().toLocalTime() : null,
                program.getActiveEndAt() != null ? program.getActiveEndAt().toLocalDate() : null,
                program.getActiveEndAt() != null ? program.getActiveEndAt().toLocalTime() : null
        );
    }

    @Transactional
    public void updateBookingInfo(Long programId, ProgramBookingInfoRequest request) {
        Program program = loadProgramOrThrow(programId);

        var open = java.time.LocalDateTime.of(request.getBookingOpenDate(), request.getBookingOpenTime());
        var close = java.time.LocalDateTime.of(request.getBookingCloseDate(), request.getBookingCloseTime());
        if (close.isBefore(open)) {
            throw new BaseException("마감 시각은 오픈 시각 이후여야 합니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        program.updateBookingOpenAt(open);
        program.updateBookingCloseAt(close);
    }

    @Transactional(readOnly = true)
    public ProgramBookingResponse getBookingInfo(Long programId) {
        Program program = loadProgramOrThrow(programId);
        return toBookingResponse(program);
    }

    private ProgramBookingResponse toBookingResponse(Program program) {
        return ProgramBookingResponse.of(
                program.getId(),
                program.getBookingOpenAt() != null ? program.getBookingOpenAt().toLocalDate() : null,
                program.getBookingOpenAt() != null ? program.getBookingOpenAt().toLocalTime() : null,
                program.getBookingCloseAt() != null ? program.getBookingCloseAt().toLocalDate() : null,
                program.getBookingCloseAt() != null ? program.getBookingCloseAt().toLocalTime() : null
        );
    }
}
