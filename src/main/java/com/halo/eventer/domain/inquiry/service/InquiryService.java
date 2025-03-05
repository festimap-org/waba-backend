package com.halo.eventer.domain.inquiry.service;

import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.inquiry.Inquiry;

import com.halo.eventer.domain.inquiry.dto.*;
import com.halo.eventer.domain.inquiry.exception.InquiryNotFoundException;
import com.halo.eventer.domain.inquiry.repository.InquiryRepository;
import com.halo.eventer.global.common.PageInfo;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {
  private final InquiryRepository inquiryRepository;
  private final PasswordEncoder passwordEncoder;
  private final FestivalService festivalService;

  public String createInquiry(Long festivalId, InquiryCreateReqDto inquiryCreateReqDto) {
    inquiryCreateReqDto.setPassword(passwordEncoder.encode(inquiryCreateReqDto.getPassword()));
    inquiryRepository.save(
        new Inquiry(festivalService.getFestival(festivalId), inquiryCreateReqDto));
    return "저장완료";
  }

  /** Admin용 전체조회 */
  public List<InquiryListElementResDto> getAllInquiryForAdmin(Long festivalId) {
    return inquiryRepository.findAllWithFestivalId(festivalId).stream()
        .map(InquiryListElementResDto::new)
        .collect(Collectors.toList());
  }

  /** Admin용 단일조회 */
  public Inquiry getInquiryForAdmin(Long id) {
    return inquiryRepository
        .findById(id)
        .orElseThrow(() -> new InquiryNotFoundException(id));
  }

  @Transactional
  public Inquiry updateInquiry(Long id, InquiryAnswerReqDto dto) {
    Inquiry inquiry = getInquiryForAdmin(id);
    inquiry.setAnswer(dto.getAnswer());
    return inquiry;
  }

  public void deleteInquiry(Long id) {
    inquiryRepository.deleteById(id);
  }

  /** 유저용 전체조회 */
  public List<InquiryListElementResDto> getAllInquiryForUser(Long festivalId) {
    List<Inquiry> inquiryList = inquiryRepository.findAllWithFestivalId(festivalId);
    List<InquiryListElementResDto> response = new ArrayList<>();
    for (Inquiry inquiry : inquiryList) {
      if (inquiry.getIsSecret()) {
        response.add(new InquiryListElementResDto(inquiry, "secret", inquiry.getUserId()));
      } else {
        response.add(
            new InquiryListElementResDto(inquiry, inquiry.getTitle(), inquiry.getUserId()));
      }
    }
    return response;
  }

  public InquiryResDto getInquiryForUser(Long id, InquiryUserReqDto dto) {
    Inquiry inquiry =
        inquiryRepository
            .findById(id)
            .orElseThrow(() -> new InquiryNotFoundException(id));

    if (inquiry.getIsSecret()) {
      if (!dto.getUserId().equals(inquiry.getUserId())
          || !passwordEncoder.matches(dto.getPassword(), inquiry.getPassword())) {
        throw new BaseException("비밀번호가 틀렸거나 아이디가 올바르지 않습니다.", ErrorCode.INVALID_INPUT_VALUE);
      }
    }
    return new InquiryResDto(inquiry);
  }

  /** 페이징 조회 */
  public InquiryPageResDto getInquiryByPaging(Long festivalId, Integer page, Integer size) {
    Page<Inquiry> inquiries =
        inquiryRepository.findAllByFestivalId(
            festivalId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")));

    PageInfo pageInfo = new PageInfo(page, size, inquiries);
    List<InquiryListElementResDto> list =
        inquiries.getContent().stream()
            .map(InquiryListElementResDto::new)
            .collect(Collectors.toList());
    return new InquiryPageResDto(list, pageInfo);
  }

  /** no offset 페이징 조회 */
  public InquiryNoOffsetPagingListDto getEventPagingList(
      Long festivalId, Long lastId, Integer size) {
    List<Inquiry> inquiryList;
    if (lastId == 0) {
      inquiryList = inquiryRepository.getFirstPage(festivalId, size + 1);
    } else {
      inquiryList = inquiryRepository.getNextPageByInquiryIdAndLastId(festivalId, lastId, size + 1);
    }
    Boolean isLast = inquiryList.size() < size + 1;
    if (!isLast) {
      inquiryList.remove(inquiryList.size() - 1);
    }
    return new InquiryNoOffsetPagingListDto(inquiryList, isLast);
  }
}
