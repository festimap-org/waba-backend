package com.halo.eventer.domain.vote.controller;


import com.halo.eventer.domain.vote.dto.*;
import com.halo.eventer.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;
    /**
     * 투표 생성
     * */
    @PostMapping("/vote")
    public VoteBackOfficeListDto createVote(@RequestBody VoteCreateReqDto voteCreateReqDto){
        return new VoteBackOfficeListDto(voteService.createVote(voteCreateReqDto));
    }
    /**
     * 투표 리스트 조회 (백오피스 용)
     * * */
    @GetMapping("/vote/backOffice")
    public VoteBackOfficeListDto getBackOfficeList(@RequestParam("festivalId") Long festivalId){
        return new VoteBackOfficeListDto(voteService.getVoteListForBackOffice(festivalId));
    }
    /**
     * 투표 단일 조회 (백오피스 용)
     * */
    @GetMapping("/vote/backOffice/{voteId}")
    public VoteGetResDto getVote(@PathVariable("voteId") Long voteId){
        return new VoteGetResDto(voteService.getVote(voteId));
    }
    /**
     * 투표 수정 (백오피스 용)
     * */
    @PatchMapping("/vote/backOffice/{voteId}")
    public VoteGetResDto updateVote(@PathVariable("voteId") Long voteId,
                                    @RequestBody VoteUpdateReqDto voteUpdateReqDto){
        return new VoteGetResDto(voteService.updateVote(voteId,voteUpdateReqDto));
    }
    /**
     * 투표 삭제 ( 백오피스 용)
     * */
    @DeleteMapping("/vote/backOffice/{voteId}")
    public VoteBackOfficeListDto deleteVote(@PathVariable("voteId") Long voteId,
                                            @RequestParam("festivalId") Long festivalId){
        return new VoteBackOfficeListDto(voteService.deleteVoteId(voteId,festivalId));
    }
    /**
     * 리스트 조회 (Client 용)
     * */
    @GetMapping("/vote/client")
    public VoteClientGetListDto getClientList(@RequestParam("festivalId") Long festivalId){
        return new VoteClientGetListDto(voteService.getVoteListForClient(festivalId));
    }
    /**
     * 좋아요 누르기 (client 용)
     * */
    @PostMapping("/vote/client/{voteId}/like")
    public Long increaseLikeCnt(@PathVariable("voteId") Long voteId, HttpServletRequest request, HttpServletResponse response){
        return voteService.increaseLikeCnt(voteId, request, response);
    }
}
