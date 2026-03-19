package com.halo.eventer.domain.vote.controller;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.vote.dto.request.VoteCreateRequest;
import com.halo.eventer.domain.vote.dto.request.VoteInfoUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.VoteScheduleUpdateRequest;
import com.halo.eventer.domain.vote.dto.response.VoteDetailResponse;
import com.halo.eventer.domain.vote.dto.response.VoteInfoResponse;
import com.halo.eventer.domain.vote.dto.response.VoteResponse;
import com.halo.eventer.domain.vote.dto.response.VoteScheduleResponse;
import com.halo.eventer.domain.vote.service.VoteAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "투표 관리 (관리자)", description = "Festi Choice 투표 관리 API")
public class VoteAdminController {

    private final VoteAdminService voteAdminService;

    @Operation(summary = "투표 생성(Only Title)")
    @PostMapping("/festivals/{festivalId}/votes")
    public ResponseEntity<Void> createVote(
            @Min(1) @PathVariable("festivalId") Long festivalId,
            @Valid @RequestBody VoteCreateRequest request) {
        voteAdminService.createVote(festivalId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "축제별 투표 목록 조회")
    @GetMapping("/festivals/{festivalId}/votes")
    public ResponseEntity<List<VoteResponse>> getVotesByFestival(
            @Min(1) @PathVariable("festivalId") Long festivalId) {
        return ResponseEntity.ok(voteAdminService.getVotesByFestival(festivalId));
    }

    @Operation(summary = "투표 기본 정보 조회 (제목/이미지/표시 설정)")
    @GetMapping("/votes/{voteId}/info")
    public ResponseEntity<VoteInfoResponse> getVoteInfo(@Min(1) @PathVariable("voteId") Long voteId) {
        return ResponseEntity.ok(voteAdminService.getVoteInfo(voteId));
    }

    @Operation(summary = "투표 일정 조회 (오픈/노출 시간)")
    @GetMapping("/votes/{voteId}/schedule")
    public ResponseEntity<VoteScheduleResponse> getVoteSchedule(@Min(1) @PathVariable("voteId") Long voteId) {
        return ResponseEntity.ok(voteAdminService.getVoteSchedule(voteId));
    }

    @Operation(summary = "투표 상세 조회")
    @GetMapping("/votes/{voteId}")
    public ResponseEntity<VoteDetailResponse> getVote(@Min(1) @PathVariable("voteId") Long voteId) {
        return ResponseEntity.ok(voteAdminService.getVote(voteId));
    }

    @Operation(summary = "투표 기본 정보 수정 (제목/이미지/표시 설정)")
    @PatchMapping("/votes/{voteId}/info")
    public ResponseEntity<Void> updateVoteInfo(
            @Min(1) @PathVariable("voteId") Long voteId,
            @Valid @RequestBody VoteInfoUpdateRequest request) {
        voteAdminService.updateVoteInfo(voteId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "투표 일정 수정 (오픈/노출 시간)")
    @PatchMapping("/votes/{voteId}/schedule")
    public ResponseEntity<Void> updateVoteSchedule(
            @Min(1) @PathVariable("voteId") Long voteId,
            @Valid @RequestBody VoteScheduleUpdateRequest request) {
        voteAdminService.updateVoteSchedule(voteId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "투표 삭제")
    @DeleteMapping("/votes/{voteId}")
    public ResponseEntity<Void> deleteVote(@Min(1) @PathVariable("voteId") Long voteId) {
        voteAdminService.deleteVote(voteId);
        return ResponseEntity.ok().build();
    }
}
