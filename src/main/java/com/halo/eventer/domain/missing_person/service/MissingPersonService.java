package com.halo.eventer.domain.missing_person.service;


import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.missing_person.MissingPerson;
import com.halo.eventer.domain.missing_person.dto.MissingPersonDto;
import com.halo.eventer.domain.missing_person.repository.MissingPersonRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissingPersonService {

    private final MissingPersonRepository missingPersonRepository;
    private final FestivalService festivalService;
    // 실종자 찾기 신청
    public void createMissingPerson(Long festivalId,MissingPersonDto missingPersonDto){
        Festival festival = festivalService.getFestival(festivalId);
        missingPersonRepository.save(new MissingPerson(missingPersonDto,festival));
    }
    // 실종자 전체 조회
    public List<MissingPerson> getAllMissingPersonList(){
        return missingPersonRepository.findAll();
    }
    //실종자 단일 조회
    public MissingPerson getMissingPerson(Long id){
        return missingPersonRepository.findById(id).orElseThrow(()->new BaseException("실종자가 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
    }
    //실종자 팝업 선택
    @Transactional
    public void checkPopup(Long id, boolean check) {
        MissingPerson person = missingPersonRepository.findById(id).orElseThrow(()->new BaseException("실종자가 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
        person.setPopup(check);
    }
    //실종자 삭제
    @Transactional
    public void deleteMissingPerson(Long missingId) {
        missingPersonRepository.delete(missingPersonRepository.findById(missingId).orElseThrow(()->new BaseException("실종자가 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND)));
    }
    //팝업 리스트 조회
    public List<MissingPerson> getPopupList(Long festivalId) {
        return missingPersonRepository.findAllByFestival_idAndPopup(festivalId,true);
    }
}
