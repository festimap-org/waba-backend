package com.halo.eventer.user.controller;

import com.halo.eventer.user.dto.LostDto;
import com.halo.eventer.user.dto.UrgentDto;
import com.halo.eventer.user.dto.MissingPersonDto;
import com.halo.eventer.user.service.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    //분실물 등록
    @PostMapping("/lostItem")
    public ResponseEntity<?> uploadLost(@RequestBody LostDto lostDto){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.uploadLostItem(lostDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //분실물 수정
    @PatchMapping("/lostItem/{itemId}")
    public ResponseEntity<String> updateLostItem(@PathVariable(name = "itemId") Long itemId,
                                                 @RequestBody LostDto lostDto){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.updateLostItem(itemId,lostDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //분실물 삭제
    @DeleteMapping("/lostItem/{itemId}")
    public ResponseEntity<String> deleteLostItem(@PathVariable(name = "itemId") Long itemId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.deleteLostItem(itemId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //팝업 수정
    @PatchMapping("/missingPerson/popup")
    public ResponseEntity<?> selectMissingPersonPopup(@Parameter(name = "missingId") Long missingId,
                                         @Parameter(name ="check") boolean check){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.checkPopup(missingId,check));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    //실종자 수정
    @PatchMapping("missingPerson/{missingId}")
    public ResponseEntity<String> updateMissingPerson(@PathVariable(name = "missingId") Long missingId,
                                                      @RequestBody MissingPersonDto missingPersonDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.updateMissingPerson(missingId,missingPersonDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //실종자 삭제
    @DeleteMapping("/missingPerson/{missingId}")
    public ResponseEntity<String> deleteMissingPerson(@PathVariable(name = "missingId") Long missingId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.deleteMissingPerson(missingId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //긴급 공지 생성
    @PostMapping("/urgent")
    public ResponseEntity<?> uploadUrgent(@RequestBody UrgentDto urgentDto){
        try {
            log.info("{} {}",urgentDto.getContent(),urgentDto.getTitle());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.uploadUrgent(urgentDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //긴급공지 전체조회
    @GetMapping("/urgent")
    public ResponseEntity<?> getUrgents(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.getUrgents());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //긴급공지 단일조회
    @GetMapping("/urgent/{urgentId}")
    public ResponseEntity<?> getUrgent(@PathVariable(name = "urgentId") Long urgentId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.getUrgent(urgentId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //긴급 공지 수정
    @PatchMapping("/urgent/{urgentId}")
    public ResponseEntity<String> updateUrgent(@PathVariable(name = "urgentId") Long urgentId,
                                               @RequestBody UrgentDto urgentDto){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.updateUrgent(urgentId, urgentDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //긴급 공지 삭제
    @DeleteMapping("/urgent/{urgentId}")
    public ResponseEntity<String> deleteUrgent(@PathVariable(name = "urgentId") Long urgentId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.deleteUrgent(urgentId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //긴급 공지 팝업 선택
    @PatchMapping("/urgent/popup")
    public ResponseEntity<?> selectUrgentPopup(@Parameter(name = "urgentId") Long urgentId,
                                         @Parameter(name ="check") boolean check){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.checkUrgentPopup(urgentId,check));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
