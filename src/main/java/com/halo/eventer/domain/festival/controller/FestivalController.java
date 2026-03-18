package com.halo.eventer.domain.festival.controller;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.image.dto.FileDto;
import com.halo.eventer.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/festivals")
@Tag(name = "축제", description = "축제 관리 API")
public class FestivalController {

    private final FestivalService festivalService;

    @Operation(summary = "축제 생성", description = "새로운 축제를 생성합니다. 생성한 회원이 owner로 설정됩니다.")
    @PostMapping
    public ResponseEntity<FestivalCreateResponseDto> createFestival(
            @Valid @RequestBody FestivalCreateDto festivalCreateDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Festival festival = festivalService.create(festivalCreateDto, userDetails.getMember());

        return ResponseEntity.status(HttpStatus.CREATED).body(new FestivalCreateResponseDto(festival));
    }

    @Operation(summary = "축제 상세 조회", description = "축제 ID로 축제 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<FestivalResDto> getFestival(@Min(1) @PathVariable Long id) {
        return ResponseEntity.ok(festivalService.findById(id));
    }

    @Operation(summary = "축제 목록 조회", description = "모든 축제 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<FestivalSummaryDto>> getFestivals() {
        return ResponseEntity.ok(festivalService.findAll());
    }

    @Operation(summary = "축제 수정", description = "축제 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<FestivalResDto> updateFestival(
            @Min(1) @PathVariable Long id, @Valid @RequestBody FestivalCreateDto festivalCreateDto) {

        return ResponseEntity.ok(festivalService.update(id, festivalCreateDto));
    }

    @Operation(summary = "축제 삭제", description = "축제를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(@Min(1) @PathVariable Long id) {
        festivalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "축제 색상 수정", description = "축제의 테마 색상을 수정합니다.")
    @PatchMapping("/{festivalId}/color")
    public ResponseEntity<Void> updateColor(
            @Min(1) @PathVariable Long festivalId, @Valid @RequestBody ColorDto colorDto) {

        festivalService.updateColor(festivalId, colorDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "축제 로고 수정", description = "축제의 로고 이미지를 수정합니다.")
    @PatchMapping("/{festivalId}/logo")
    public ResponseEntity<Void> updateLogo(@Min(1) @PathVariable Long festivalId, @RequestBody FileDto fileDto) {

        festivalService.updateLogo(festivalId, fileDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "축제 위치 수정", description = "축제의 위치 정보를 수정합니다.")
    @PatchMapping("/{festivalId}/location")
    public ResponseEntity<FestivalResDto> updateFestivalLocation(
            @Min(1) @PathVariable Long festivalId, @Valid @RequestBody FestivalLocationDto festivalLocationDto) {

        return ResponseEntity.ok(festivalService.updateLocation(festivalId, festivalLocationDto));
    }

    @Operation(summary = "축제 위치 조회", description = "축제의 위치 정보를 조회합니다.")
    @GetMapping("/{festivalId}/location")
    public ResponseEntity<FestivalLocationDto> getFestivalLocationInfo(@PathVariable Long festivalId) {
        return ResponseEntity.ok(festivalService.getFestivalLocationInfo(festivalId));
    }

    @Operation(summary = "축제 이름 수정", description = "축제의 이름을 수정합니다.")
    @PatchMapping("/{festivalId}/name")
    public ResponseEntity<FestivalResDto> updateFestivalName(
            @Min(1) @PathVariable Long festivalId, @Valid @RequestBody FestivalNameReqDto festivalNameReqDto) {

        return ResponseEntity.ok(festivalService.updateFestivalName(festivalId, festivalNameReqDto));
    }

    @Operation(summary = "축제 서브 도메인 수정", description = "축제의 서브 도메인을 수정합니다.")
    @PatchMapping("/{festivalId}/sub-domain")
    public ResponseEntity<FestivalResDto> updateFestivalSubDomain(
            @PathVariable Long festivalId, @Valid @RequestBody FestivalSubDomainReqDto festivalSubdomainReqDto) {

        return ResponseEntity.ok(festivalService.updateFestivalSubDomain(festivalId, festivalSubdomainReqDto));
    }
}
