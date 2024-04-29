package com.halo.eventer.user.service;


import com.halo.eventer.user.dto.LostDto;
import com.halo.eventer.user.dto.MissingPersonDto;
import com.halo.eventer.user.LostItem;
import com.halo.eventer.user.MissingPerson;
import com.halo.eventer.user.repository.LostItemRepository;
import com.halo.eventer.user.repository.MissingPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final LostItemRepository lostItemRepository;
    private final MissingPersonRepository missingPersonRepository;

    //분실물 단일 조회
    public LostDto getLostItem(Long id)throws Exception{
        LostDto response = new LostDto(lostItemRepository.findById(id).orElseThrow(()->new Exception("분실물 정보가 존재하지 않습니다.")));
        return response;
    }

    //분실물 전체 조회
    public List<LostItem> getAllLostItems(){
        List<LostItem> responses = lostItemRepository.findAll().stream().collect(Collectors.toList());
        return responses;
    }

    // 실종자 찾기 신청
    public String uploadMissingPerson(MissingPersonDto missingPerson){
        missingPersonRepository.save(new MissingPerson(missingPerson));
        return "등록완료";
    }

    // 실종자 전체 조회
    public List<MissingPerson> getAllMissingPeople(){
        List<MissingPerson> responses = missingPersonRepository.findAll().stream().collect(Collectors.toList());
        return responses;
    }

    //실종자 단일 조회
    public MissingPerson getMissingPerson(Long id)throws Exception{
         return missingPersonRepository.findById(id).orElseThrow(()->new Exception("해당 실종자가 존재하지 않습니다."));
    }
}
