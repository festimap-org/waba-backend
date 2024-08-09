package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.domain.stamp.dto.MissionInfoGetDto;
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
    private final EncryptService encryptService;

    private Stamp getStamp(StampGetDto stampGetDto) {
        String userInfo = stampGetDto.getName() + "/" + stampGetDto.getPhone();
        String encryptedInfo = encryptService.encryptInfo(userInfo);
        Stamp stamp = stampRepository.findByUserInfo(encryptedInfo).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));

        return stamp;
    }

    @Transactional
    public MissionInfoGetDto getMissionInfo(StampGetDto stampGetDto) {
        String userInfo = stampGetDto.getName() + "/" + stampGetDto.getPhone();
        String encryptedInfo = encryptService.encryptInfo(userInfo);

        Stamp stamp = stampRepository.findByUserInfo(encryptedInfo).orElse(new Stamp(encryptedInfo));
        stampRepository.save(stamp);
        return new MissionInfoGetDto(stamp);
    }

    @Transactional
    public String updateStamp(StampGetDto stampGetDto, int missionId) {
        Stamp stamp = getStamp(stampGetDto);

        switch (missionId) {
            case 1: stamp.updateMission1(); break;
            case 2: stamp.updateMission2(); break;
            case 3: stamp.updateMission3(); break;
            case 4: stamp.updateMission4(); break;
            case 5: stamp.updateMission5(); break;
            case 6: stamp.updateMission6(); break;
            case 7: stamp.updateMission7(); break;
            default:
                throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        }
        stampRepository.save(stamp);

        return "업데이트 성공";
    }

    @Transactional
    public String checkFinish(StampGetDto stampGetDto) {
        Stamp stamp = getStamp(stampGetDto);
        if (stamp.isMission1() && stamp.isMission2() && stamp.isMission3() && stamp.isMission4() && stamp.isMission5()
                && stamp.isMission6() && stamp.isMission7()) {
            stamp.setFinished();
            stampRepository.save(stamp);
            return "스탬프 투어 완료";
        }
        else return "미완료";
    }

}
