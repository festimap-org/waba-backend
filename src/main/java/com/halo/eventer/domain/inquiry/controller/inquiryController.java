package com.halo.eventer.domain.inquiry.controller;



import com.halo.eventer.domain.inquiry.dto.*;
import com.halo.eventer.domain.inquiry.service.InquiryService;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class inquiryController {

    private final InquiryService inquiryService;
    /**
     * 문의사항 등록
     * */
    @PostMapping
    public String createInquiry(@RequestParam("festivalId") Long festivalId ,
                                @RequestBody InquiryCreateReqDto inquiryCreateReqDto){
        return inquiryService.createInquiry(festivalId,inquiryCreateReqDto);
    }
    /**
     *  문의사항 전체 조회 (admin 용)
     * */
    @GetMapping("/forAdmin")
    public InquiryListResDto getAllInquiryForAdmin(@RequestParam("festivalId") Long festivalId){
        return new InquiryListResDto(inquiryService.getAllInquiryForAdmin(festivalId));
    }

    /**
     *  문의사항 단일 조회 (admin용)
     * */
    @PostMapping("/forAdmin/{inquiryId}")
    public InquiryResDto getInquiryForAdmin(@PathVariable("inquiryId") Long id){
        return new InquiryResDto(inquiryService.getInquiryForAdmin(id));
    }

    @PatchMapping("/forAdmin")
    public InquiryResDto updateInquiry(@RequestParam("inquiryId") Long id,
                              @RequestBody InquiryAnswerReqDto answerReqDto){
        return new InquiryResDto(inquiryService.updateInquiry(id, answerReqDto));
    }

    @DeleteMapping("/forAdmin")
    public void deleteInquiry(@RequestParam("inquiryId") Long id){
        inquiryService.deleteInquiry(id);
    }

    /**
     *  문의사항 전체 조회 (유저 용)
     * */
    @GetMapping("/forUser")
    public InquiryListResDto getAllInquiryForUser(@RequestParam("festivalId") Long festivalId){
        return new InquiryListResDto(inquiryService.getAllInquiryForUser(festivalId));
    }

    /**
     *  문의사항 단일 조회 (유저용)
     * */
    @PostMapping("/forUser/{inquiryId}")
    public InquiryResDto getInquiry(@PathVariable("inquiryId") Long id, @RequestBody InquiryUserReqDto dto){
        return inquiryService.getInquiryForUser(id,dto);
    }
    /**
     * 문의사항 페이징 (Offset 페이징)
     * */
    @GetMapping("/paging")
    public InquiryPageResDto getInquiryByPaging(@RequestParam("festivalId") Long festivalId,
                                                @RequestParam("page") @Min(0) Integer page,
                                                @RequestParam("size") @Min(1) Integer size){
        return inquiryService.getInquiryByPaging(festivalId,page,size);
    }
}
