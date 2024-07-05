package com.halo.eventer.domain.notice.controller;

import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.domain.notice.dto.*;
import com.halo.eventer.domain.notice.swagger.*;
import com.halo.eventer.global.common.ArticleType;
import com.halo.eventer.domain.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지사항", description = "공지사항과 관련된 모든 것")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    /** 공지사항 등록 */
    @CreateNoticeApi
    @PostMapping
    public String registerNotice(@RequestBody NoticeRegisterDto NoticeRegisterDto,
                                 @RequestParam("festivalId") Long id) {
        return noticeService.registerNotice(NoticeRegisterDto, id);
    }

    /** 공지사항 타입별로 조회 */
    @NoticeGetsApi
    @GetMapping("/{festivalId}")
    public NoticeInquireListDto inquireNoticeList(@PathVariable Long festivalId,
                                                    @RequestParam("type") ArticleType type) {
        return noticeService.inquireNoticeList(festivalId, type);
    }


    /** 단일 공지사항 / 이벤트 조회하기 */
    @GetNoticeReqApi
    @GetMapping("/{festivalId}/{noticeId}")
    public Notice getNotice(@PathVariable("festivalId") Long festivalId,
                            @PathVariable("noticeId") Long noticeId) {
        return noticeService.getNotice(noticeId);
    }

    /** 배너 등록, 해제 */
    @SelectBannerApi
    @PostMapping("/banner")
    public String changeBanner(@RequestParam("noticeId") Long noticeId,
                               @RequestParam("pick") Boolean pick) {
        return noticeService.changeBanner(noticeId,pick);
    }

    /** 등록된 배너 전체 조회 */
    @GetMapping("/banner")
    public RegisteredBannerGetListDto getRegisteredBanner(@RequestParam("festivalId") Long festivalId){
        return noticeService.getRegisteredBanner(festivalId);
    }

    /** 배너 순서 등록 */
    @PatchMapping("/banner")
    public String editBannerRank(@RequestBody BannerEditListDto bannerEditListDto){
        return noticeService.editBannerRank(bannerEditListDto);
    }


    /** 공지사항 수정 */
    @PatchMapping("/{noticeId}")
    public String updateNotice(@PathVariable("noticeId") Long id,
                               @RequestBody NoticeRegisterDto NoticeRegisterDto) {
        return noticeService.updateNotice(id, NoticeRegisterDto);
    }


    /** 공지사항 삭제 */
    @DeleteMapping("/{noticeId}")
    public String deleteNotice(@PathVariable("noticeId") Long id) {
        return noticeService.deleteNotice(id);
    }
}
