package com.halo.eventer.domain.inquiry.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.inquiry.Inquiry;

import com.halo.eventer.domain.inquiry.InquiryConstants;
import com.halo.eventer.domain.inquiry.dto.*;
import com.halo.eventer.domain.inquiry.exception.InquiryNotFoundException;
import com.halo.eventer.domain.inquiry.exception.InquiryUnauthorizedAccessException;
import com.halo.eventer.domain.inquiry.repository.InquiryRepository;
import com.halo.eventer.global.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {
  private final InquiryRepository inquiryRepository;
  private final PasswordService passwordService;
  private final FestivalRepository festivalRepository;

  public String create(Long festivalId, InquiryCreateReqDto inquiryCreateReqDto) {
    Festival festival = festivalRepository
            .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    String encodedPassword = passwordService.encode(inquiryCreateReqDto.getPassword());
    Inquiry inquiry = new Inquiry(festival,inquiryCreateReqDto,encodedPassword);
    inquiryRepository.save(inquiry);
    return "저장완료";
  }

  public List<InquiryItemDto> findAllInquiryForAdmin(Long festivalId) {
    return findAllByFestivalId(festivalId).stream()
            .map(InquiryItemDto::new)
            .collect(Collectors.toList());
  }

  public Inquiry findInquiryForAdmin(Long id) {
    return inquiryRepository.findById(id).orElseThrow(() -> new InquiryNotFoundException(id));
  }

  @Transactional
  public Inquiry updateInquiry(Long id, InquiryAnswerReqDto dto) {
    Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(() -> new InquiryNotFoundException(id));
    inquiry.registerAnswer(dto.getAnswer());
    return inquiry;
  }

  public void deleteInquiry(Long id) {
    inquiryRepository.deleteById(id);
  }

  public List<InquiryItemDto> findAllInquiryForUser(Long festivalId) {
    return findAllByFestivalId(festivalId).stream()
            .map(this::createInquiryItemDtoWithTitleVisibility)
            .collect(Collectors.toList());
  }

  public InquiryResDto getInquiryForUser(Long id, InquiryUserReqDto dto) {
    Inquiry inquiry = inquiryRepository.findById(id)
            .orElseThrow(() -> new InquiryNotFoundException(id));

    validateAccess(inquiry, dto);

    return new InquiryResDto(inquiry);
  }

  public InquiryNoOffsetPageDto getInquiriesWithNoOffsetPaging(Long festivalId, Long lastId) {
    int pageSize = InquiryConstants.INQUIRY_PAGE_SIZE;
    List<Inquiry> inquiries = inquiryRepository.getPageByFestivalIdAndLastId(
            festivalId, lastId, pageSize + 1);

    boolean isLast = inquiries.size() <= pageSize;
    if (!isLast)
      inquiries.remove(inquiries.size() - 1);

    return new InquiryNoOffsetPageDto(inquiries, isLast);
  }

  private List<Inquiry> findAllByFestivalId(Long festivalId){
    return inquiryRepository.findAllByFestivalId(festivalId);
  }

  private InquiryItemDto createInquiryItemDtoWithTitleVisibility(Inquiry inquiry) {
    String title = getDisplayableTitle(inquiry);
    return new InquiryItemDto(inquiry, title, inquiry.getUserId());
  }

  private String getDisplayableTitle(Inquiry inquiry) {
    return inquiry.isSecret() ? InquiryConstants.PRIVATE_INQUIRY_TITLE : inquiry.getTitle();
  }

  private void validateAccess(Inquiry inquiry, InquiryUserReqDto dto) {
    if (!inquiry.isSecret()) {
      return;
    }

    boolean isOwner = dto.getUserId().equals(inquiry.getUserId());
    boolean isPasswordCorrect = passwordService.matches(dto.getPassword(), inquiry.getPassword());

    if (!isOwner || !isPasswordCorrect) {
      throw new InquiryUnauthorizedAccessException();
    }
  }
}
