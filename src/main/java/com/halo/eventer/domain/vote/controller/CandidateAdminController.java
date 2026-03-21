package com.halo.eventer.domain.vote.controller;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.vote.dto.request.CandidateBulkDeleteRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateCreateRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateEnableUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.DisplayOrderUpdateRequest;
import com.halo.eventer.domain.vote.dto.response.CandidateListResponse;
import com.halo.eventer.domain.vote.dto.response.CandidateResponse;
import com.halo.eventer.domain.vote.service.CandidateAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "후보 관리 (관리자)", description = "Festi Choice 후보 관리 API")
public class CandidateAdminController {

    private final CandidateAdminService candidateAdminService;

    @Operation(summary = "후보 전체 조회")
    @GetMapping("/votes/{voteId}/candidates")
    public ResponseEntity<List<CandidateListResponse>> getCandidates(@Min(1) @PathVariable("voteId") Long voteId) {
        return ResponseEntity.ok(candidateAdminService.getCandidates(voteId));
    }

    @Operation(summary = "후보 단건 등록")
    @PostMapping("/votes/{voteId}/candidates")
    public ResponseEntity<Void> addCandidate(
            @Min(1) @PathVariable("voteId") Long voteId, @Valid @RequestBody CandidateCreateRequest request) {
        candidateAdminService.addCandidate(voteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "후보 수정")
    @PatchMapping("/votes/{voteId}/candidates/{candidateId}")
    public ResponseEntity<CandidateResponse> updateCandidate(
            @Min(1) @PathVariable("voteId") Long voteId,
            @Min(1) @PathVariable("candidateId") Long candidateId,
            @Valid @RequestBody CandidateUpdateRequest request) {
        return ResponseEntity.ok(candidateAdminService.updateCandidate(voteId, candidateId, request));
    }

    @Operation(summary = "후보 선택 활성화/비활성화")
    @PatchMapping("/votes/{voteId}/candidates/enabled")
    public ResponseEntity<Void> updateCandidatesEnabled(
            @Min(1) @PathVariable("voteId") Long voteId, @Valid @RequestBody CandidateEnableUpdateRequest request) {
        candidateAdminService.updateCandidatesEnabled(voteId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "후보 선택 삭제")
    @DeleteMapping("/votes/{voteId}/candidates")
    public ResponseEntity<Void> deleteCandidates(
            @Min(1) @PathVariable("voteId") Long voteId, @Valid @RequestBody CandidateBulkDeleteRequest request) {
        candidateAdminService.deleteCandidates(voteId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "후보 노출 순서 변경")
    @PatchMapping("/votes/{voteId}/candidates/display-order")
    public ResponseEntity<Void> updateDisplayOrder(
            @Min(1) @PathVariable("voteId") Long voteId, @Valid @RequestBody DisplayOrderUpdateRequest request) {
        candidateAdminService.updateDisplayOrder(voteId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "선택 후보 엑셀 다운로드")
    @GetMapping("/votes/{voteId}/candidates/excel")
    public ResponseEntity<Void> downloadCandidatesExcel(
            @Min(1) @PathVariable("voteId") Long voteId, @RequestParam("candidateIds") List<Long> candidateIds) {
        // TODO: 선택된 후보 엑셀 다운로드 구현
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "후보 엑셀 등록 양식 다운로드")
    @GetMapping("/candidates/excel/template")
    public ResponseEntity<Void> downloadExcelTemplate() {
        // TODO: 엑셀 등록 양식 다운로드 구현
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "후보 엑셀 일괄 등록")
    @PostMapping("/votes/{voteId}/candidates/excel")
    public ResponseEntity<Void> addCandidatesByExcel(@Min(1) @PathVariable("voteId") Long voteId) {
        // TODO: 엑셀 일괄 등록 구현
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
