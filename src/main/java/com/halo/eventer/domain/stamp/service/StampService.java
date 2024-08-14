package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampInfo;
import com.halo.eventer.domain.stamp.dto.MissionInfoGetDto;
import com.halo.eventer.domain.stamp.repository.StampInfoRepository;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.domain.stamp.dto.StampInfoGetDto;
import com.halo.eventer.domain.stamp.dto.StampGetDto;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class StampService {
    private final StampRepository stampRepository;
    private final StampInfoRepository stampInfoRepository;
    private final EncryptService encryptService;

    private StampInfo getStampInfoFromUuid(String uuid) {
        StampInfo stampInfo = stampInfoRepository.findByStampUuid(uuid).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        return stampInfo;
    }

    @Transactional
    public StampInfoGetDto getStampInfo(StampGetDto stampGetDto) {
        String userInfo = stampGetDto.getName() + "/" + stampGetDto.getPhone();
        String encryptedInfo = encryptService.encryptInfo(userInfo);

        Stamp stamp = stampRepository.findByUserInfo(encryptedInfo).orElse(new Stamp(encryptedInfo));
        stampRepository.save(stamp);

        StampInfo stampInfo = getStampInfoFromUuid(stamp.getUuid());
        stampInfo.setParticipantCount(stampGetDto.getParticipantCount());
        stampInfoRepository.save(stampInfo);

        return new StampInfoGetDto(stampInfo);
    }

    public MissionInfoGetDto getMissionInfo(String uuid) {
        StampInfo stampInfo = getStampInfoFromUuid(uuid);
        return new MissionInfoGetDto(stampInfo);
    }


    @Transactional
    public String updateStamp(String uuid, int missionId) {
        StampInfo stampInfo = getStampInfoFromUuid(uuid);

        switch (missionId) {
            case 1: stampInfo.updateMission1(); break;
            case 2: stampInfo.updateMission2(); break;
            case 3: stampInfo.updateMission3(); break;
            case 4: stampInfo.updateMission4(); break;
            case 5: stampInfo.updateMission5(); break;
            case 6: stampInfo.updateMission6(); break;
            default:
                throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        }

        stampInfoRepository.save(stampInfo);

        return "업데이트 성공";
    }

    @Transactional
    public String checkFinish(String uuid) {
        StampInfo stampInfo = getStampInfoFromUuid(uuid);
        if (stampInfo.isMission1() && stampInfo.isMission2() && stampInfo.isMission3() && stampInfo.isMission4() && stampInfo.isMission5()
                && stampInfo.isMission6()) {
            stampInfo.setFinished();
            stampInfoRepository.save(stampInfo);

            return "스탬프 투어 완료";
        }
        else return "미완료";
    }
}
