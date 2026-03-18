package com.halo.eventer.domain.festival.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.enums.FestivalStatus;
import com.halo.eventer.domain.festival.exception.FestivalAlreadyExistsException;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.image.dto.FileDto;
import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.MemberRole;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FestivalService {

    private final FestivalRepository festivalRepository;

    @Transactional
    public Festival create(FestivalCreateDto festivalCreateDto, Member owner) {
        validateUniqueFestival(festivalCreateDto);

        Festival festival = Festival.from(festivalCreateDto);
        festival.applyDefaultMapCategory();
        festival.assignOwner(owner);
        festival.updateStatus(FestivalStatus.ACTIVE);

        return festivalRepository.save(festival);
    }

    public FestivalResDto findById(Long id) {
        Festival festival = loadFestivalOrThrow(id);
        return FestivalResDto.from(festival);
    }

    public List<FestivalSummaryDto> findAll() {
        return festivalRepository.findAll().stream()
                .map(FestivalSummaryDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FestivalResDto update(Long id, FestivalCreateDto festivalCreateDto) {
        Festival festival = loadFestivalOrThrow(id);

        validateUniqueFestivalName(id, festivalCreateDto.getName());
        validateUniqueFestivalSubDomain(id, festivalCreateDto.getSubDomain());

        festival.updateFestival(festivalCreateDto);
        return FestivalResDto.from(festival);
    }

    @Transactional
    public void delete(Long id) {
        Festival festival = loadFestivalOrThrow(id);
        festivalRepository.delete(festival);
    }

    @Transactional
    public void updateColor(Long id, ColorDto colorDto) {
        Festival festival = loadFestivalOrThrow(id);
        festival.updateColor(colorDto);
    }

    @Transactional
    public void updateLogo(Long id, FileDto fileDto) {
        Festival festival = loadFestivalOrThrow(id);
        festival.updateLogo(fileDto);
    }

    public FestivalSummaryDto findBySubAddress(String subAddress) {
        Festival festival = festivalRepository
                .findBySubAddress(subAddress)
                .orElseThrow(() -> new FestivalNotFoundException(subAddress));
        return new FestivalSummaryDto(festival);
    }

    @Transactional
    public FestivalResDto updateLocation(Long id, FestivalLocationDto festivalLocationDto) {
        Festival festival = loadFestivalOrThrow(id);
        festival.updateLocation(festivalLocationDto);
        return FestivalResDto.from(festival);
    }

    public FestivalLocationDto getFestivalLocationInfo(Long festivalId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        return new FestivalLocationDto(festival);
    }

    @Transactional
    public FestivalResDto updateFestivalName(Long festivalId, FestivalNameReqDto dto) {
        Festival festival = loadFestivalOrThrow(festivalId);

        validateUniqueFestivalName(festivalId, dto.getName());

        festival.updateName(dto.getName());
        return FestivalResDto.from(festival);
    }

    @Transactional
    public FestivalResDto updateFestivalSubDomain(Long festivalId, FestivalSubDomainReqDto dto) {
        Festival festival = loadFestivalOrThrow(festivalId);

        validateUniqueFestivalSubDomain(festivalId, dto.getSubDomain());

        festival.updateSubDomain(dto.getSubDomain());
        return FestivalResDto.from(festival);
    }

    @Transactional(readOnly = true)
    public List<FestivalSummaryDto> findFestivalsByMember(Member member) {
        if (member.getRole() == MemberRole.SUPER_ADMIN) {
            return findAll();
        }

        return festivalRepository.findByOwner(member).stream()
                .map(FestivalSummaryDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveFestival(Long festivalId, Member admin) {
        validateSuperAdmin(admin);

        Festival festival = loadFestivalOrThrow(festivalId);
        festival.updateStatus(FestivalStatus.ACTIVE);
    }

    @Transactional
    public void updateFestivalStatus(Long festivalId, FestivalStatus status, Member admin) {
        validateSuperAdmin(admin);

        Festival festival = loadFestivalOrThrow(festivalId);
        festival.updateStatus(status);
    }

    private Festival loadFestivalOrThrow(Long id) {
        return festivalRepository.findById(id).orElseThrow(() -> new FestivalNotFoundException(id));
    }

    private void validateUniqueFestival(FestivalCreateDto festivalCreateDto) {
        boolean existsByName =
                festivalRepository.findByName(festivalCreateDto.getName()).isPresent();
        boolean existsBySubDomain = festivalRepository
                .findBySubAddress(festivalCreateDto.getSubDomain())
                .isPresent();

        if (existsByName || existsBySubDomain) {
            throw new FestivalAlreadyExistsException();
        }
    }

    private void validateUniqueFestivalName(Long festivalId, String name) {
        boolean exists = festivalRepository.existsByNameAndIdNot(name, festivalId);

        if (exists) {
            throw new FestivalAlreadyExistsException();
        }
    }

    private void validateUniqueFestivalSubDomain(Long festivalId, String subDomain) {
        boolean exists = festivalRepository.existsBySubAddressAndIdNot(subDomain, festivalId);

        if (exists) {
            throw new FestivalAlreadyExistsException();
        }
    }

    private void validateSuperAdmin(Member member) {
        if (member.getRole() != MemberRole.SUPER_ADMIN) {
            throw new BaseException(ErrorCode.UN_AUTHORIZED);
        }
    }
}
