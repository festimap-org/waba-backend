package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.dto.stamp.*;
import com.halo.eventer.domain.stamp.dto.stampUser.FinishedStampUserDto;
import com.halo.eventer.domain.stamp.exception.StampClosedException;
import com.halo.eventer.domain.stamp.exception.StampNotFoundException;
import com.halo.eventer.domain.stamp.repository.MissionRepository;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StampService {
    private final StampRepository stampRepository;
    private final FestivalRepository festivalRepository;
    private final EncryptService encryptService;
    private final MissionRepository missionRepository;

    /** 축제 id로 스탬프 생성 */
    @Transactional
    public List<StampGetDto> registerStamp(Long festivalId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        stampRepository.save(Stamp.create(festival));
        List<Stamp> stamps = stampRepository.findByFestival(festival);
        return StampGetDto.fromStampList(stamps);
    }

    /** 축제 id로 스탬프 조회 */
    @Transactional(readOnly = true)
    public List<StampGetDto> getStampByFestivalId(Long festivalId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        List<Stamp> stamps = stampRepository.findByFestival(festival);
        return StampGetDto.fromStampList(stamps);
    }

    /** 스탬프 상태 변경 */
    @Transactional
    public void updateStampOn(Long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.switchStampOn();
    }

    /** 스탬프 삭제 */
    @Transactional
    public void deleteStamp(Long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        stampRepository.delete(stamp);
    }

    /** 미션 생성 */
    @Transactional
    public void createMission(Long stampId, MissionSetListDto dto) {
        Stamp stamp = loadStampOrThrow(stampId);
        validateStampIsOpen(stamp);
        List<Mission> missions = createMissionsFromDto(dto, stamp);
        missionRepository.saveAll(missions);
    }

    /** 미션 리스트 조회 */
    @Transactional(readOnly = true)
    public List<MissionSummaryGetDto> getMissions(Long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        return MissionSummaryGetDto.fromMissionList(stamp.getMissions());
    }

    /** 해당 스탬프 유저들 조회 */
    @Transactional(readOnly = true)
    public List<StampUsersGetDto> getStampUsers(Long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        // return StampUsersGetDto.fromList(stamp.getStampUsers());
        return stamp.getStampUsers()
                .stream()
                .map(user -> new StampUsersGetDto(
                        user.getUuid(),
                        encryptService.decryptInfo(user.getName()),
                        encryptService.decryptInfo(user.getPhone()),
                        user.isFinished(),
                        user.getParticipantCount()
                ))
                .collect(Collectors.toList());
    }

    /** 모든 미션 완료한 사용자 조회 */
    public List<FinishedStampUserDto> getFinishedStampUsers(Long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        List<FinishedStampUserDto> finishedUser = stamp.getStampUsers().stream()
                .filter(StampUser::isFinished)
                .map(user -> new FinishedStampUserDto(user.getCustom() != null ? user.getCustom().getSchoolNo() : "X", encryptService.decryptInfo(user.getName()), encryptService.decryptInfo(user.getPhone())))
                .collect(Collectors.toList());

        return finishedUser;
    }

    /** 미션 성공 기준 정하기 */
    @Transactional
    public void setFinishCnt(Long stampId, Integer cnt) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.setStampFinishCnt(cnt);
    }

    private Festival loadFestivalOrThrow(Long id) {
        return festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));
    }

    private Stamp loadStampOrThrow(Long stampId) {
        return stampRepository.findById(stampId)
                .orElseThrow(() -> new StampNotFoundException(stampId));
    }


    private void validateStampIsOpen(Stamp stamp) {
        if (!stamp.isStampOn()) {
            throw new StampClosedException(stamp.getId());
        }
    }

    private List<Mission> createMissionsFromDto(MissionSetListDto dto, Stamp stamp) {
        return dto.getMissionSets().stream()
                .map(missionDto -> {
                    Mission mission = Mission.from(missionDto);
                    mission.addStamp(stamp);
                    return mission;
                })
                .collect(Collectors.toList());
    }
}
