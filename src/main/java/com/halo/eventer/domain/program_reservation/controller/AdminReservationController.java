package com.halo.eventer.domain.program_reservation.controller;

import com.halo.eventer.domain.program_reservation.dto.response.AdminSlotCalendarResponse;
import com.halo.eventer.domain.program_reservation.dto.response.ScheduleTemplateListResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.program_reservation.dto.request.ScheduleTemplateCreateRequest;
import com.halo.eventer.domain.program_reservation.dto.request.ScheduleTemplateUpdateRequest;
import com.halo.eventer.domain.program_reservation.dto.response.ScheduleTemplateDetailResponse;
import com.halo.eventer.domain.program_reservation.dto.response.ScheduleTemplateUpdateResponse;
import com.halo.eventer.domain.program_reservation.service.AdminReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
@Validated
@Tag(name = "관리자 - 프로그램 예약 관리", description = "예약 회차 관리 API")
public class AdminReservationController {
    private final AdminReservationService adminReservationService;

    /** GET 예약 회차 캘린더 조회*/
    @GetMapping("/admin/programs/{programId}/slots/calendar")
    @Operation(summary = "예약 회차 캘린더 조회(관리자)", description = "관리자 예약 회차 관리 화면에서 사용하는 날짜별 슬롯 목록을 조회합니다.")
    public AdminSlotCalendarResponse getAdminSlotCalendar(@PathVariable Long programId) {
        return adminReservationService.getAdminSlotCalendar(programId);
    }

    /** GET 기간 카드 목록 조회 */
    @GetMapping("/programs/{programId}/schedule-templates")
    @Operation(summary = "기간 카드 목록 조회", description = "특정 프로그램의 스케줄 템플릿 카드 리스트를 조회합니다.")
    public List<ScheduleTemplateListResponse> getTemplates(@PathVariable("programId") Long programId) { return adminReservationService.getTemplates(programId); }


    /** GET 상세 조회 (편집 로딩) */
    @GetMapping("/schedule-templates/{templateId}")
    @Operation(summary = "템플릿 상세 조회", description = "편집 모달에서 사용할 템플릿 설정값 전체를 조회합니다.")
    public ScheduleTemplateDetailResponse getTemplateDetail(@PathVariable("templateId") Long templateId) {
        return adminReservationService.getTemplateDetail(templateId);
    }

    /** POST 템플릿 생성 (기간 카드 추가) */
    @PostMapping("/programs/{programId}/schedule-templates")
    @Operation(summary = "템플릿 생성", description = "프로그램에 새 기간 카드(스케줄 템플릿)를 추가합니다.")
    public void createTemplate(@PathVariable("programId") Long programId, @RequestBody @Valid ScheduleTemplateCreateRequest request) {
        adminReservationService.createTemplate(programId, request);
    }

    /** PUT 템플릿 저장 및 수정 */
    @PutMapping("/schedule-templates/{templateId}")
    @Operation(summary = "템플릿 저장/수정", description = "모달에서 기간/회차/정원 변경 저장. 예약 존재 시 부분 성공 정책 적용")
    public ScheduleTemplateUpdateResponse updateTemplate(@PathVariable("templateId") Long templateId, @RequestBody @Valid ScheduleTemplateUpdateRequest request) {
        return adminReservationService.updateTemplate(templateId, request);
    }

    /** DELETE 템플릿 삭제; 전체 실패 */
    @DeleteMapping("/schedule-templates/{templateId}")
    @Operation(summary = "템플릿 삭제", description = "예약 이력이 있으면 409, 없으면 템플릿/패턴/슬롯을 일괄 삭제")
    public void deleteTemplate(@PathVariable("templateId") Long templateId) {
        adminReservationService.deleteTemplate(templateId);
    }
}
