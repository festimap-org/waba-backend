package com.halo.eventer.domain.missing_person.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.missing_person.MissingPerson;
import com.halo.eventer.domain.missing_person.dto.MissingPersonReqDto;
import com.halo.eventer.domain.missing_person.exception.MissingPersonNotFoundException;
import com.halo.eventer.domain.missing_person.repository.MissingPersonRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissingPersonService {

  private final MissingPersonRepository missingPersonRepository;
  private final FestivalService festivalService;

  // 실종자 찾기 신청
  public void createMissingPerson(Long festivalId, MissingPersonReqDto missingPersonReqDto) {
    Festival festival = festivalService.findById(festivalId);
    missingPersonRepository.save(new MissingPerson(missingPersonReqDto, festival));
  }

  // 실종자 전체 조회
  public List<MissingPerson> getAllMissingPersonList() {
    return missingPersonRepository.findAll();
  }

  // 실종자 단일 조회
  public MissingPerson getMissingPerson(Long id) {
    return missingPersonRepository
        .findById(id)
        .orElseThrow(() -> new MissingPersonNotFoundException(id));
  }

  // 실종자 수정
  @Transactional
  public void updateMissingPerson(Long id, MissingPersonReqDto missingPersonReqDto) {
    MissingPerson person =
        missingPersonRepository
            .findById(id)
            .orElseThrow(() -> new MissingPersonNotFoundException(id));
    person.update(missingPersonReqDto);
  }

  // 실종자 팝업 선택
  @Transactional
  public void checkPopup(Long id, boolean check) {
    MissingPerson person =
        missingPersonRepository
            .findById(id)
            .orElseThrow(() -> new MissingPersonNotFoundException(id));
    person.setPopup(check);
  }

  // 실종자 삭제
  @Transactional
  public void deleteMissingPerson(Long missingId) {
    missingPersonRepository.delete(
        missingPersonRepository
            .findById(missingId)
            .orElseThrow(() -> new MissingPersonNotFoundException(missingId)));
  }

  // 팝업 리스트 조회
  public List<MissingPerson> getPopupList(Long festivalId) {
    return missingPersonRepository.findAllByFestival_idAndPopup(festivalId, true);
  }
}
