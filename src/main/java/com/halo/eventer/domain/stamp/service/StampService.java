package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.dto.*;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StampService {
    private final StampRepository stampRepository;
    private final EncryptService encryptService;
    private final FestivalService festivalService;

    private Stamp getStampFromUuid(String uuid) {
        Stamp stamp = stampRepository.findByUuid(uuid).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        return stamp;
    }

    @Transactional
    public StampGetDto getStampInfo(Long festivalId, UserInfoGetDto userInfoGetDto) {
        String userInfo = userInfoGetDto.getName() + "/" + userInfoGetDto.getPhone();
        String encryptedInfo = encryptService.encryptInfo(userInfo);

        Stamp stamp = stampRepository.findByUserInfo(encryptedInfo).orElse(new Stamp(festivalService.getFestival(festivalId), encryptedInfo));
        stamp.setParticipantCount(userInfoGetDto.getParticipantCount());
        stampRepository.save(stamp);

        return new StampGetDto(stamp);
    }

    public MissionInfoGetDto getMissionInfo(String uuid) {
        Stamp stamp = getStampFromUuid(uuid);
        return new MissionInfoGetDto(stamp);
    }


    @Transactional
    public String updateStamp(String uuid, int missionId) {
        Stamp stamp = getStampFromUuid(uuid);

        switch (missionId) {
            case 1: stamp.updateMission1(); break;
            case 2: stamp.updateMission2(); break;
            case 3: stamp.updateMission3(); break;
            case 4: stamp.updateMission4(); break;
            case 5: stamp.updateMission5(); break;
            case 6: stamp.updateMission6(); break;
            default:
                throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        }
        stampRepository.save(stamp);

        return "업데이트 성공";
    }

    @Transactional
    public String checkFinish(String uuid) {
        Stamp stamp = getStampFromUuid(uuid);
        if (stamp.isMission1() && stamp.isMission2() && stamp.isMission3() && stamp.isMission4() && stamp.isMission5()
                && stamp.isMission6()) {
            stamp.setFinished();
            stampRepository.save(stamp);
            return "스탬프 투어 완료";
        }
        else return "미완료";
    }


    public StampInfoGetListDto getStampInfos(Long festivalId) {
        List<StampInfoGetDto> stampInfoGetDtos = new ArrayList<>();
        for (Stamp stamp : stampRepository.findByFestival(festivalService.getFestival(festivalId))) {
            String userInfo = encryptService.decryptInfo(stamp.getUserInfo());
            String[] parts = userInfo.split("/");

            stampInfoGetDtos.add(new StampInfoGetDto(stamp, parts[0], parts[1]));
        }
        return new StampInfoGetListDto(stampInfoGetDtos);
    }

    @Transactional
    public String deleteStamp(Long festivalId) {
        stampRepository.deleteByFestival(festivalService.getFestival(festivalId));
        return "삭제 완료";
    }

}
