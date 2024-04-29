package com.halo.eventer.user.service;


import com.halo.eventer.user.dto.LostDto;
import com.halo.eventer.user.dto.UrgentDto;
import com.halo.eventer.user.dto.MissingPersonDto;
import com.halo.eventer.user.LostItem;
import com.halo.eventer.user.MissingPerson;
import com.halo.eventer.user.Urgent;
import com.halo.eventer.user.repository.LostItemRepository;
import com.halo.eventer.user.repository.MissingPersonRepository;
import com.halo.eventer.user.repository.UrgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final LostItemRepository lostItemRepository;
    private final MissingPersonRepository missingPersonRepository;
    private final UrgentRepository urgentRepository;

    //분실물 등록
    public LostDto uploadLostItem(LostDto lostDto){
        lostItemRepository.save(new LostItem(lostDto));
        return lostDto;
    }

    //분실물 수정
    @Transactional
    public String updateLostItem(Long id,LostDto lostDto)throws Exception{
        LostItem item = lostItemRepository.findById(id).orElseThrow(()->new Exception("해당 분실물이 존재하지 않습니다."));
        item.updateItem(lostDto);
        return "수정 완료";
    }

    //분실물 삭제
    @Transactional
    public String deleteLostItem(Long id)throws Exception{
        lostItemRepository.delete(lostItemRepository.findById(id).orElseThrow(()->new Exception("해당 분실물이 존재하지 않습니다.")));
        return "삭제 완료";
    }


    // 팝업 선택
    @Transactional
    public String checkPopup(Long id, boolean check) throws Exception{
        MissingPerson person = missingPersonRepository.findById(id).orElseThrow(()->new Exception("실종자 정보가 존재하지 않습니다."));
        person.setPopup(check);
        return "반영 완료";
    }


    @Transactional
    public String updateMissingPerson(Long id, MissingPersonDto missingPersonDto)throws Exception{
        MissingPerson person = missingPersonRepository.findById(id).orElseThrow(()->new Exception("해당 분실물이 존재하지 않습니다."));
        person.update(missingPersonDto);
        return "수정 완료";
    }
    @Transactional
    public String deleteMissingPerson(Long missingId) throws Exception{
        missingPersonRepository.delete(missingPersonRepository.findById(missingId).orElseThrow(()->new Exception("해당 실종자 정보가 존재하지 않습니다.")));
        return "삭제 완료";
    }

    //긴급 공지 생성
    @Transactional
    public Urgent uploadUrgent(UrgentDto urgentDto) {
        Urgent urgent = urgentRepository.save(new Urgent(urgentDto));
        log.info(urgent.getContent(),urgent.getTitle());
        return urgent;
    }

    //긴급 공지 전체 조회
    public List<Urgent> getUrgents() {
        return urgentRepository.findAll();
    }

    //긴급 공지 단일 조회
    public UrgentDto getUrgent(Long urgentId) throws Exception {
        return new UrgentDto(urgentRepository.findById(urgentId).orElseThrow(()->new Exception("해당 긴급 공지가 존재하지 않습니다.")));
    }

    @Transactional
    public String updateUrgent(Long urgentId, UrgentDto urgentDto)throws Exception {
        Urgent urgent = urgentRepository.findById(urgentId).orElseThrow(()->new Exception("해당 긴급공지가 존재하지 않습니다."));
        urgent.update(urgentDto);
        return "수정 완료";
    }

    @Transactional
    public String deleteUrgent(Long urgentId) throws Exception {
        urgentRepository.delete(urgentRepository.findById(urgentId).orElseThrow(()->new Exception("해당 긴급공지 정보가 존재하지 않습니다.")));
        return "삭제 완료";
    }

    @Transactional
    public String checkUrgentPopup(Long urgentId, boolean check) throws Exception{
        Urgent urgent = urgentRepository.findById(urgentId).orElseThrow(()->new Exception("실종자 정보가 존재하지 않습니다."));
        urgent.setPopup(check);
        return "반영 완료";
    }
}
