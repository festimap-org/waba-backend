package com.halo.eventer.domain.missing_person.controller;

import com.halo.eventer.domain.manager.Manager;
import com.halo.eventer.domain.manager.service.ManagerService;
import com.halo.eventer.domain.missing_person.dto.MissingPersonReqDto;
import com.halo.eventer.domain.missing_person.dto.MissingPersonListDto;
import com.halo.eventer.domain.missing_person.dto.MissingPersonResDto;
import com.halo.eventer.domain.missing_person.service.MissingPersonService;
import com.halo.eventer.infra.naver.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missingPerson")
@Slf4j
public class MissingPersonController {

    private final MissingPersonService missingPersonService;
    private final SmsService smsService;
    private final ManagerService managerService;
    //실종자 등록
    @PostMapping()
    public void uploadMissingPerson(@RequestParam("festivalId") Long festivalId,
                                    @RequestBody MissingPersonReqDto missingPersonReqDto) throws Exception{
        missingPersonService.createMissingPerson(festivalId, missingPersonReqDto);

        List<String> phoneList = managerService.getManagerList(festivalId).stream()
                .map(Manager::getPhoneNo).collect(Collectors.toList());
        if(!phoneList.isEmpty()){
            smsService.sendSms(missingPersonReqDto,phoneList);
        }

    }
    //실종자 전체 조회
    @GetMapping()
    public MissingPersonListDto getMissingPersons(@RequestParam("festivalId") Long festivalId){
        return new MissingPersonListDto(missingPersonService.getAllMissingPersonList(festivalId));
    }
    //실종자 단일 조회
    @GetMapping("/{missingPersonId}")
    public MissingPersonResDto getMissingPerson(@PathVariable(name="missingPersonId") Long missingPersonId){
        return new MissingPersonResDto(missingPersonService.getMissingPerson(missingPersonId));
    }
    //실종자 정보 수정
    @PatchMapping()
    public void updateMissingPerson(@RequestParam(name = "missingPersonId") Long missingPersonId,
                                                    @RequestBody MissingPersonReqDto missingPersonReqDto){
        missingPersonService.updateMissingPerson(missingPersonId, missingPersonReqDto);
    }
    //실종자 팝업 수정
    @PatchMapping("/popup")
    public void selectMissingPersonPopup(@RequestParam(name = "missingPersonId") Long missingPersonId,
                                         @RequestParam(name ="check") boolean check){
        missingPersonService.checkPopup(missingPersonId,check);
    }
    //실종자 삭제
    @DeleteMapping("/{missingId}")
    public void deleteMissingPerson(@PathVariable(name = "missingId") Long missingId){
        missingPersonService.deleteMissingPerson(missingId);
    }
}
