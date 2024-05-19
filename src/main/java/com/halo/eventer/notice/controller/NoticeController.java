package com.halo.eventer.notice.controller;

import com.halo.eventer.common.ArticleType;
import com.halo.eventer.notice.dto.*;
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

    @CreateNoticeApi
    @PostMapping
    public String registerNotice(@RequestBody NoticeReqDto noticeReqDto,
                                            @RequestParam("festivalId") Long id) {
        return noticeService.registerNotice(noticeReqDto, id);
    }

    @NoticeGetsApi
    @GetMapping("/{festivalId}")
    public List<GetAllNoticeResDto> inquireNotices(@PathVariable Long festivalId,
                                                   @RequestParam("type") ArticleType type) {
        return noticeService.inquireNotices(festivalId, type);
    }


    /**   단일 공지사항 / 이벤트 조회하기   */
    @GetNoticeReqApi
    @GetNoticeResApi
    @GetMapping("/{festivalId}/{noticeId}")
    public GetNoticeResDto getNotice(@PathVariable("festivalId") Long festivalId,
                                     @PathVariable("noticeId") Long noticeId) {
        return noticeService.getNotice(noticeId);
    }

    @SelectBannerApi
    @PostMapping("/banner")
    public String changeBanner(@RequestParam("noticeId") Long noticeId,
                               @RequestParam("pick") Boolean pick)
    {
        return noticeService.changeBanner(noticeId,pick);
    }

    //등록된 배너 조회
    @GetMapping("/banner")
    public List<BannerResDto> getRegisteredBanner(@RequestParam("festivalId") Long festivalId){
        return noticeService.getRegisteredBanner(festivalId);
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
