package com.halo.eventer.domain.parking.service;

import com.halo.eventer.domain.parking.repository.ParkingNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingNoticeService {

    private final ParkingNoticeRepository parkingNoticeRepository;

    // 1. 주차 안내 등록
    @Transactional
    public void create(){

    }

    // 2. 주차 안내 리스트 조회
    @Transactional(readOnly = true)
    public void getParkingNotices(){

    }
    // 3. 주차 안내 제목, 내용 수정
    @Transactional
    public void updateParkingNoticeInfo(){

    }

    // 4. 주차 안내 visible 수정
    @Transactional
    public void updateParkingNoticeVisible(){

    }

    // 5. 주차 안내 삭제
    @Transactional
    public void delete(){

    }

}
