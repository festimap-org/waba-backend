package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.dto.stamp.*;
import com.halo.eventer.domain.stamp.dto.stampUser.FinishedStampUserDto;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StampService {
    private final StampRepository stampRepository;
    private final FestivalService festivalService;
    private final EncryptService encryptService;

    /** 축제 id로 스탬프 조회 */
    public Stamp getStamp(Long stampId) {
        return stampRepository.findById(stampId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
    }

    /** 축제 id로 스탬프 생성 */
    @Transactional
    public StampGetListDto registerStamp(Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId);
        stampRepository.save(new Stamp(festival));

        List<Stamp> stamps = stampRepository.findByFestival(festivalService.getFestival(festivalId));
        List<StampGetDto> stampGetDtos = StampGetDto.fromStampList(stamps);
        return new StampGetListDto(stampGetDtos);
    }

    /** 축제 id로 스탬프 조회 */
    public StampGetListDto getStampByFestivalId(Long festivalId) {
        List<Stamp> stamps = stampRepository.findByFestival(festivalService.getFestival(festivalId));
        List<StampGetDto> stampGetDtos = StampGetDto.fromStampList(stamps);
        return new StampGetListDto(stampGetDtos);
    }

    /** 스탬프 상태 변경 */
    @Transactional
    public String updateStampOn(Long stampId) {
        Stamp stamp = stampRepository.findById(stampId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        if (stamp.isStampOn()) stamp.setStampOn(false);
        else stamp.setStampOn(true);

        stampRepository.save(stamp);

        return "스탬프 상태 변경 성공";
    }

    /** 스탬프 삭제 */
    @Transactional
    public String deleteStamp(Long stampId) {
        stampRepository.deleteById(stampId);
        return "삭제 완료";
    }

    /** 미션 생성 */
    @Transactional
    public String setMission(Long stampId, MissionSetListDto dto) {
        Stamp stamp = getStamp(stampId);
        if (!stamp.isStampOn()) throw new BaseException("종료된 스탬프 투어입니다.", ErrorCode.ELEMENT_NOT_FOUND);

        List<Mission> missions = dto.getMissionSets().stream()
                .map(m -> new Mission(
                        m.getBoothId(),
                        m.getTitle(),
                        m.getContent(),
                        m.getPlace(),
                        m.getTime(),
                        m.getClearedThumbnail(),
                        m.getNotClearedThumbnail(),
                        stamp,
                        m.getDetailThumbnail()))
                .collect(Collectors.toList());
        stamp.setMissions(missions);

        stampRepository.save(stamp);

        return "미션 생성 성공";
    }

    /** 미션 리스트 조회 */
    public MissionSummaryGetListDto getMissions(Long stampId) {
        Stamp stamp = getStamp(stampId);
        List<MissionSummaryGetDto> missionSummaryGetDtos = stamp.getMissions().stream()
                .map(m -> new MissionSummaryGetDto(
                            m.getId(),
                            m.getTitle(),
                            m.getClearedThumbnail(),
                            m.getNotClearedThumbnail()
                        ))
                .collect(Collectors.toList());

        return new MissionSummaryGetListDto(missionSummaryGetDtos);
    }

    /** 해당 스탬프 유저들 조회 */
    public StampUsersGetListDto getStampUsers(Long stampId) {
        Stamp stamp = getStamp(stampId);
        List<StampUsersGetDto> users = stamp.getStampUsers().stream()
                .map(user -> new StampUsersGetDto(
                        user.getUuid(),
                        encryptService.decryptInfo(user.getName()),
                        encryptService.decryptInfo(user.getPhone()),
                        user.isFinished(),
                        user.getParticipantCount()
                ))
                .collect(Collectors.toList());

        return new StampUsersGetListDto(users);
    }

    /** 모든 미션 완료한 사용자 조회 */
    public List<FinishedStampUserDto> getFinishedStampUsers(Long stampId) {
        Stamp stamp = getStamp(stampId);
        List<FinishedStampUserDto> finishedUser = stamp.getStampUsers().stream()
                .filter(StampUser::isFinished)
                .map(user -> new FinishedStampUserDto(user.getCustom() != null ? user.getCustom().getSchoolNo() : "X", encryptService.decryptInfo(user.getName()), encryptService.decryptInfo(user.getPhone())))
                .collect(Collectors.toList());

        return finishedUser;
    }

    /** 미션 성공 기준 정하기 */
    @Transactional
    public String setFinishCnt(Long stampId, Integer cnt) {
        Stamp stamp = getStamp(stampId);
        stamp.setStampFinishCnt(cnt);
        return "변경 완료";
    }
}
