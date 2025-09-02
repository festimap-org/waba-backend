package com.halo.eventer.domain.stamp.service.v2;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.UserMission;
import com.halo.eventer.domain.stamp.dto.mission.request.MissionClearReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.MissionCleared;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.SortType;
import com.halo.eventer.domain.stamp.dto.stampUser.request.MissionCompletionUpdateReq;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserDetailResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserSummaryResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.UserMissionStatusResDto;
import com.halo.eventer.domain.stamp.exception.StampNotFoundException;
import com.halo.eventer.domain.stamp.exception.StampUserNotFoundException;
import com.halo.eventer.domain.stamp.exception.UserMissionNotFoundException;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.domain.stamp.repository.StampUserRepository;
import com.halo.eventer.global.common.page.PageInfo;
import com.halo.eventer.global.common.page.PagedResponse;
import com.halo.eventer.global.utils.EncryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StampUserAdminService {

    private final StampRepository stampRepository;
    private final StampUserRepository stampUserRepository;
    private final EncryptService encryptService;

    @Transactional(readOnly = true)
    public PagedResponse<StampUserSummaryResDto> getStampUsers(
            long festivalId, long stampId, String q, MissionCleared cleared, int page, int size, SortType sortType) {
        ensureStamp(festivalId, stampId);
        String sortProperty = sortType.getProperty();
        Boolean clearedFilter = cleared.toBoolean();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortProperty));
        Page<StampUser> result = selectQueryBySearchTerm(stampId, q, clearedFilter, pageable);
        return convertToPagedResponse(result);
    }

    @Transactional(readOnly = true)
    public StampUserDetailResDto getUserDetail(long festivalId, long stampId, long userId) {
        ensureStamp(festivalId, stampId);
        StampUser stampUser = loadStampUserFromIdAndStampId(userId, stampId);
        List<UserMissionStatusResDto> missions = stampUser.getUserMissions().stream()
                .sorted(Comparator.comparing(um -> um.getMission().getId()))
                .map(um -> new UserMissionStatusResDto(
                        um.getId(), um.getMission().getId(), um.getMission().getTitle(), um.isComplete()))
                .toList();

        return new StampUserDetailResDto(
                decryptStampUserName(stampUser.getName()),
                decryptStampUserPhone(stampUser.getPhone()),
                stampUser.getUuid(),
                stampUser.isFinished(),
                missions,
                stampUser.getExtraText(),
                stampUser.getParticipantCount());
    }

    @Transactional
    public void updateUserMissionState(
            long festivalId, long stampId, long userId, long userMissionId, MissionClearReqDto request) {
        ensureStamp(festivalId, stampId);
        StampUser stampUser = loadStampUserFromIdAndStampId(userId, stampId);
        UserMission target = stampUser.getUserMissions().stream()
                .filter(um -> um.getId().equals(userMissionId))
                .findFirst()
                .orElseThrow(() -> new UserMissionNotFoundException(userMissionId));

        // 4) 상태 변경
        if (request.isClear()) {
            target.markAsComplete();
        } else {
            target.markAsIncomplete();
        }
        syncUserFinishedFlag(stampUser);
    }

    @Transactional
    public void updateStampUserPrizeAndFinished(
            long festivalId, long stampId, long userId, MissionCompletionUpdateReq request) {
        ensureStamp(festivalId, stampId);
        StampUser su = loadUserWithMissionsOrThrow(stampId, userId);
        su.markAsFinished(request.isFinished());
        su.updateReceivedPrizeName(request.getPrizeName());
    }

    private void ensureStamp(long festivalId, long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festivalId);
    }

    private Stamp loadStampOrThrow(long stampId) {
        return stampRepository.findById(stampId).orElseThrow(() -> new StampNotFoundException(stampId));
    }

    private StampUser loadStampUserFromIdAndStampId(long userId, long stampId) {
        return stampUserRepository
                .findByIdAndStampIdWithMissions(userId, stampId)
                .orElseThrow(() -> new StampUserNotFoundException(userId));
    }

    private void syncUserFinishedFlag(StampUser su) {
        long completed =
                su.getUserMissions().stream().filter(UserMission::isComplete).count();
        boolean finished = completed >= su.getStamp().getFinishCount();
        su.markAsFinished(finished);
    }

    private StampUser loadUserWithMissionsOrThrow(long stampId, long userId) {
        return stampUserRepository
                .findByIdAndStampIdWithMissions(userId, stampId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Page<StampUser> selectQueryBySearchTerm(long stampId, String q, Boolean cleared, Pageable pageable) {
        return (q == null || q.isBlank())
                ? stampUserRepository.findByStampId(stampId, cleared, pageable)
                : stampUserRepository.searchUsers(stampId, q.trim(), cleared, pageable);
    }

    private PagedResponse<StampUserSummaryResDto> convertToPagedResponse(Page<StampUser> result) {
        List<StampUserSummaryResDto> content = result.getContent().stream()
                .map(su -> new StampUserSummaryResDto(
                        su.getId(),
                        decryptStampUserName(su.getName()),
                        decryptStampUserPhone(su.getPhone()),
                        su.getUuid(),
                        su.isFinished(),
                        su.getCreatedAt()))
                .toList();
        PageInfo pageInfo = PageInfo.builder()
                .pageNumber(result.getNumber() + 1)
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();

        return PagedResponse.<StampUserSummaryResDto>builder()
                .content(content)
                .pageInfo(pageInfo)
                .build();
    }

    private StampUserDetailResDto toDetailDto(StampUser su) {
        List<UserMissionStatusResDto> missions = su.getUserMissions().stream()
                .sorted(Comparator.comparing(um -> um.getMission().getId()))
                .map(um -> new UserMissionStatusResDto(
                        um.getId(), um.getMission().getId(), um.getMission().getTitle(), um.isComplete()))
                .toList();

        return new StampUserDetailResDto(
                decryptStampUserName(su.getName()),
                decryptStampUserPhone(su.getPhone()),
                su.getUuid(),
                su.isFinished(),
                missions,
                su.getExtraText(),
                su.getParticipantCount());
    }

    private String decryptStampUserName(String encodedName) {
        return encryptService.decryptInfo(encodedName);
    }

    private String decryptStampUserPhone(String encodedPhone) {
        return encryptService.decryptInfo(encodedPhone);
    }
}
