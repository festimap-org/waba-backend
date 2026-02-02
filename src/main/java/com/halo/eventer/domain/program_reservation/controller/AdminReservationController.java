package com.halo.eventer.domain.program_reservation.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.program_reservation.dto.*;
import com.halo.eventer.domain.program_reservation.service.FestivalCommonTemplateService;
import com.halo.eventer.domain.program_reservation.service.ProgramService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
@Validated
@Tag(name = "관리자 - 프로그램 예약 관리", description = "프로그램 및 공통 템플릿 관리 API")
public class AdminReservationController {
    private final ProgramService programService;
    private final FestivalCommonTemplateService templateService;

    @PostMapping("/programs")
    @Operation(summary = "프로그램 생성", description = "프로그램 생성시 기본으로 미노출 설정됨")
    public void create(@RequestParam("festivalId") Long festivalId, @RequestBody @Valid ProgramCreateRequest request) { programService.create(festivalId, request); }

    @PatchMapping("/programs/{programId}/name")
    @Operation(summary = "프로그램 이름 변경", description = "body로 새 이름(name)만 전달")
    public void rename(@PathVariable("programId") Long programId, @RequestBody @Valid ProgramRenameRequest request) { programService.rename(programId, request); }

    @DeleteMapping("/programs/{programId}")
    @Operation(summary = "프로그램 삭제")
    public void deleteProgram(@PathVariable("programId") Long programId) { programService.delete(programId); }

    @GetMapping("/programs")
    @Operation(summary = "프로그램 리스트 조회", description = "프로그램명으로 검색 가능 (선택)")
    public ProgramListResponse getPrograms(
            @RequestParam("festivalId") Long festivalId,
            @RequestParam(value = "name", required = false) String name) {
        return ProgramListResponse.of(programService.getList(festivalId, name));
    }

    @GetMapping("/programs/{programId}")
    @Operation(summary = "프로그램 상세 조회", description = "프로그램 정보, 태그/블록, 축제 공통 템플릿 포함")
    public ProgramDetailResponse getProgramDetail(@PathVariable("programId") Long programId) {
        return programService.getDetail(programId);
    }

    @PatchMapping("/programs/{programId}")
    @Operation(summary = "프로그램 상세 정보 수정", description = "썸네일, 태그, 이용료, 소요시간 등 상세 정보 저장. 태그와 블록은 전체 교체")
    public void updateProgramDetail(
            @PathVariable("programId") Long programId, @RequestBody @Valid ProgramUpdateRequest request) {
        programService.updateDetail(programId, request);
    }

    @PatchMapping("/programs/{programId}/active")
    @Operation(summary = "프로그램 노출/미노출 토글", description = "toggleActive()를 호출해 isActive 상태를 반전시킵니다.")
    public void toggleProgramActive(@PathVariable("programId") Long programId) {
        programService.toggleActive(programId);
    }

    @GetMapping("/programs/{programId}/active-info")
    @Operation(summary = "프로그램 노출 일정 조회")
    public ProgramActiveResponse getActiveInfo(@PathVariable("programId") Long programId) { return programService.getActiveInfo(programId); }

    @PostMapping("/programs/{programId}/active-info")
    @Operation(
            summary = "프로그램 노출 일정 설정",
            description = "시작/종료 일시를 설정합니다. 예시) activeStartDate=2026-02-01, activeStartTime=10:00, activeEndDate=2026-02-28, activeEndTime=10:00 (HH:mm)"
    )
    public void updateActiveInfo(@PathVariable("programId") Long programId, @RequestBody @Valid ProgramActiveInfoRequest request) {
        programService.updateActiveInfo(programId, request);
    }

    @GetMapping("/templates")
    @Operation(summary = "공통 템플릿 목록 조회", description = "축제에 등록된 공통 템플릿을 sortOrder 순으로 조회합니다.")
    public List<TemplateResponse> getTemplates(@RequestParam("festivalId") Long festivalId) {
        return templateService.getList(festivalId);
    }

    @PutMapping("/templates")
    @Operation(summary = "공통 템플릿 전체 저장", description = "요청 배열대로 순서를 저장합니다.")
    public void saveAllTemplates(
            @RequestParam("festivalId") Long festivalId,
            @RequestBody @Valid TemplateSaveAllRequest request) {
        templateService.saveAll(festivalId, request);
    }

    @DeleteMapping("/templates/{templateId}")
    @Operation(summary = "공통 템플릿 삭제")
    public void deleteTemplate(@PathVariable("templateId") Long templateId) {
        templateService.delete(templateId);
    }
}
