package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.dto.LoginDto;
import com.halo.eventer.domain.stamp.dto.SignupDto;
import com.halo.eventer.domain.stamp.dto.StampUserInfoGetDto;
import com.halo.eventer.domain.stamp.dto.StampUserInfoGetListDto;
import com.halo.eventer.domain.stamp.repository.StampUserRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StampUserService {
    private final StampUserRepository stampUserRepository;
    private final EncryptService encryptService;
    private final StampService stampService;

    private StampUser getStampUserFromUuid(String uuid) {
        StampUser stampUser = stampUserRepository.findByUuid(uuid).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        return stampUser;
    }

    @Transactional
    public StampUser signup(Long stampId, SignupDto signupDto) {
        if (!stampService.getStamp(stampId).isStampOn()) throw new BaseException("종료된 스탬프 투어입니다.", ErrorCode.ELEMENT_NOT_FOUND);

        String encryptedInfo = encryptService.encryptInfo(signupDto.getName() + "/" + signupDto.getPhone());
        if (stampUserRepository.existsByStampIdAndUserInfo(stampId, encryptedInfo)) throw new BaseException(ErrorCode.ELEMENT_DUPLICATED);

        StampUser stampUser = new StampUser(stampService.getStamp(stampId), encryptedInfo, signupDto.getParticipantCount());
        stampUserRepository.save(stampUser);

        return stampUser;
    }

    public StampUser login(Long stampId, LoginDto loginDto) {
        String encryptedInfo = encryptService.encryptInfo(loginDto.getName() + "/" + loginDto.getPhone());
        StampUser stampUser = stampUserRepository.findByStampIdAndUserInfo(stampId, encryptedInfo).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));

        return stampUser;
    }


    public StampUser getMissionInfo(String uuid) {
        StampUser stampUser = getStampUserFromUuid(uuid);
        return stampUser;
    }


    @Transactional
    public String updateStamp(String uuid, int missionId) {
        StampUser stampUser = getStampUserFromUuid(uuid);

        switch (missionId) {
            case 1: stampUser.updateMission1(); break;
            case 2: stampUser.updateMission2(); break;
            case 3: stampUser.updateMission3(); break;
            case 4: stampUser.updateMission4(); break;
            case 5: stampUser.updateMission5(); break;
            case 6: stampUser.updateMission6(); break;
            default:
                throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        }
        stampUserRepository.save(stampUser);

        return "업데이트 성공";
    }

    @Transactional
    public String checkFinish(String uuid) {
        StampUser stampUser = getStampUserFromUuid(uuid);
        if (stampUser.isMission1() && stampUser.isMission2() && stampUser.isMission3() && stampUser.isMission4() && stampUser.isMission5()
                && stampUser.isMission6()) {
            stampUser.setFinished();
            stampUserRepository.save(stampUser);
            return "스탬프 투어 완료";
        }
        else return "미완료";
    }


    public StampUserInfoGetListDto getStampInfos(Long stampId) {
        List<StampUserInfoGetDto> stampUserInfoGetDtos = new ArrayList<>();
        for (StampUser stampUser : stampUserRepository.findByStamp(stampService.getStamp(stampId))) {
            String userInfo = encryptService.decryptInfo(stampUser.getUserInfo());
            String[] parts = userInfo.split("/");

            stampUserInfoGetDtos.add(new StampUserInfoGetDto(stampUser, parts[0], parts[1]));
        }
        return new StampUserInfoGetListDto(stampUserInfoGetDtos);
    }

    @Transactional
    public String deleteStampByUuid(String uuid) {
        StampUser stampUser = getStampUserFromUuid(uuid);
        stampUserRepository.delete(stampUser);
        return "삭제 완료";
    }
}
