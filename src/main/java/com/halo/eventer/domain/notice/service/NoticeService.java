package com.halo.eventer.domain.content.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.image.ImageRepository;
import com.halo.eventer.domain.content.Content;
import com.halo.eventer.domain.content.dto.*;
import com.halo.eventer.domain.content.exception.ContentNotFoundException;
import com.halo.eventer.domain.content.repository.NoticeRepository;
import com.halo.eventer.global.common.ArticleType;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final ImageRepository imageRepository;

  private final FestivalRepository festivalRepository;

  /** 공지사항 등록 */
  @Transactional
  public String registerNotice(NoticeRegisterDto NoticeRegisterDto, Long festivalId) {
    Festival festival = festivalRepository.findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    Content content = new Content(NoticeRegisterDto);
    content.setFestival(festival);
    content.setImages(
        NoticeRegisterDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
    noticeRepository.save(content);
    return "저장 완료";
  }

  /** 공지사항 타입별로 조회 */
  @Transactional
  public NoticeInquireListDto inquireNoticeList(Long festivalId, ArticleType type) {
    Festival festival = festivalRepository.findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    List<Content> contents = noticeRepository.findAllByFestivalAndType(festival, type);

    return new NoticeInquireListDto(
        contents.stream().map(NoticeInquireDto::new).collect(Collectors.toList()));
  }

  /** 단일 공지사항 / 이벤트 조회하기 */
  @Transactional
  public Content getNotice(Long id) {
    return noticeRepository.findById(id).orElseThrow(() -> new ContentNotFoundException(id));
  }

  /** 배너 등록, 해제 */
  @Transactional
  public String changeBanner(Long noticeId, Boolean pick) {
    Content content = getNotice(noticeId);
    content.setPicked(pick);
    return "반영 완료";
  }

  /** 공지사항 수정 */
  @Transactional
  public String updateNotice(Long noticeId, NoticeRegisterDto NoticeRegisterDto) {
    Content content = getNotice(noticeId);

    imageRepository.deleteByIdIn(NoticeRegisterDto.getDeleteIds());
    content.setAll(NoticeRegisterDto);
    content.setImages(
        NoticeRegisterDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
    return "수정완료";
  }

  /** 공지사항 삭제 */
  @Transactional
  public String deleteNotice(Long noticeId) {
    Content content = getNotice(noticeId);
    noticeRepository.delete(content);
    return "삭제완료";
  }

  /** 등록된 배너 전체 조회 */
  public RegisteredBannerGetListDto getRegisteredBanner(Long festivalId) {
    return new RegisteredBannerGetListDto(
        noticeRepository.findAllByPickedAndFestival_Id(true, festivalId).stream()
            .map(RegisteredBannerGetDto::new)
            .collect(Collectors.toList()));
  }

  /** 배너 순서 등록 */
  @Transactional
  public String editBannerRank(BannerEditListDto bannerEditListDto) {
    List<Content> contents =
        noticeRepository.findAllById(
            bannerEditListDto.getBannerEditListDto().stream()
                .map(BannerEditDto::getNoticeId)
                .collect(Collectors.toList()));

    for (Content content : contents) {
      for (BannerEditDto b : bannerEditListDto.getBannerEditListDto()) {
        if (b.getNoticeId() == content.getId()) {
          content.setRank(b.getRank());
          break;
        }
      }
    }
    return "수정완료";
  }
}
