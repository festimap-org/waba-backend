package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.dto.stamp.*;
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
                        stamp))
                .collect(Collectors.toList());
        stamp.setMissions(missions);

        stampRepository.save(stamp);

        return "미션 생성 성공";
    }

    /** 미션 조회 */
    public MissionGetListDto getMissions(Long stampId) {
        Stamp stamp = getStamp(stampId);
        List<MissionGetDto> missionGetDtos = stamp.getMissions().stream()
                .map(m -> new MissionGetDto(
                            m.getId(),
                            m.getTitle()))
                .collect(Collectors.toList());

        return new MissionGetListDto(missionGetDtos);
    }

    /** 해당 스탬프 유저들 조회 */
    public StampUsersGetListDto getStampUsers(Long stampId) {
        Stamp stamp = getStamp(stampId);
        List<StampUsersGetDto> users = stamp.getStampUsers().stream()
                .map(user -> new StampUsersGetDto(user.getUuid()))
                .collect(Collectors.toList());

        return new StampUsersGetListDto(users);
    }
}
