package com.halo.eventer.notice.controller;

import com.halo.eventer.common.ArticleType;
import com.halo.eventer.notice.dto.BannerEditReqDto;
import com.halo.eventer.notice.dto.NoticeReqDto;
import com.halo.eventer.notice.service.NoticeService;
import com.halo.eventer.notice.swagger.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Tag(name = "공지사항", description = "공지사항과 관련된 모든 것")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;


    /**   공지사항 생성하기   */
    @CreateNoticeReqApi
    @CreateNoticeResApi
    @PostMapping
    public ResponseEntity<?> registerNotice(@RequestBody NoticeReqDto noticeReqDto,
                                            @RequestParam("festivalId") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(noticeService.registerNotice(noticeReqDto,id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    /**   공지사항 / 이벤트 리스트 조회하기   */
    @GetNoticesReqApi
    @GetNoticesResApi
    @GetMapping("/{festivalId}")
    public ResponseEntity<?> inquireNotices(@PathVariable Long festivalId,
                                            @RequestParam("type") ArticleType type) {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.inquireNotices(festivalId,type));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    /**   단일 공지사항 / 이벤트 조회하기   */
    @GetNoticeReqApi
    @GetNoticeResApi
    @GetMapping("/{festivalId}/{noticeId}")
    public ResponseEntity<?> getNotice(@PathVariable("festivalId") Long festivalId,
            @PathVariable("noticeId") Long noticeId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.getNotice(noticeId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @SelectBannerApi
    @PostMapping("/banner")
    public ResponseEntity<?> changeBanner(@RequestParam("noticeId") Long noticeId,
                                          @RequestParam("pick") Boolean pick)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.changeBanner(noticeId,pick));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //등록된 배너 조회
    @GetMapping("/banner")
    public ResponseEntity<?> getRegisteredBanner(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.getRegisteredBanner());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    // 배너 순서 등록
    @PatchMapping("/banner")
    public ResponseEntity<?> editBannerRank(@RequestBody List<BannerEditReqDto> bannerEditReqDtos){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.editBannerRank(bannerEditReqDtos));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @PatchMapping("/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable("noticeId") Long id,
                                          @RequestBody NoticeReqDto noticeReqDto){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.updateNotice(id,noticeReqDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }



    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(noticeService.deleteNotice(id));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
