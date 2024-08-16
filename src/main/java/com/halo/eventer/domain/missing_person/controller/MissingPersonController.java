package com.halo.eventer.domain.missing_person.controller;

import com.halo.eventer.domain.missing_person.dto.MissingPersonDto;
import com.halo.eventer.domain.missing_person.dto.MissingPersonListDto;
import com.halo.eventer.domain.missing_person.service.MissingPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missingPerson")
public class MissingPersonController {

    private final MissingPersonService missingPersonService;
    //실종자 등록
    @PostMapping()
    public void uploadMissingPerson(@RequestParam("festivalId") Long festivalId,@RequestBody MissingPersonDto missingPersonDto){
        missingPersonService.createMissingPerson(festivalId,missingPersonDto);
    }
    //실종자 전체 조회
    @GetMapping()
    public MissingPersonListDto getMissingPerson(){
        return new MissingPersonListDto(missingPersonService.getAllMissingPersonList());
    }
    //실종자 단일 조회
    @GetMapping("/{missingPersonId}")
    public MissingPersonDto getMissingPerson(@PathVariable(name="missingPersonId") Long missingPersonId){
        return new MissingPersonDto(missingPersonService.getMissingPerson(missingPersonId));
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
