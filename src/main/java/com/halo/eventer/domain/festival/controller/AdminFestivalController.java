package com.halo.eventer.domain.festival.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.halo.eventer.domain.festival.dto.FestivalSummaryDto;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/festivals")
@RequiredArgsConstructor
@Tag(name = "관리자 축제", description = "관리자(AGENCY) 축제 관리 API")
public class AdminFestivalController {

    private final FestivalService festivalService;

    @Operation(summary = "내 축제 목록 조회", description = "로그인한 관리자가 소유한 축제 목록을 조회합니다. SUPER_ADMIN은 모든 축제를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<FestivalSummaryDto>> getMyFestivals(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<FestivalSummaryDto> festivals = festivalService.findFestivalsByMember(userDetails.getMember());
        return ResponseEntity.ok(festivals);
    }
}
