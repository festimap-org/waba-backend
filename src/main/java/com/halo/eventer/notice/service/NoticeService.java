package com.halo.eventer.notice.service;



import com.halo.eventer.common.ArticleType;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.image.Image;
import com.halo.eventer.notice.Notice;
import com.halo.eventer.notice.dto.*;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.image.ImageRepository;
import com.halo.eventer.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FestivalRepository festivalRepository;

    private final ImageRepository imageRepository;

    @Transactional
    public String registerNotice(NoticeReqDto noticeReqDto, Long id) throws Exception {

        Festival festival = festivalRepository.findById(id).orElseThrow(()-> new Exception("축제가 존재하지 않습니다."));

        Notice notice = new Notice(noticeReqDto);
        notice.setFestival(festival);
        noticeRepository.save(notice);

        List<Image> images = noticeReqDto.getImages().stream().map(o-> new Image(o)).collect(Collectors.toList());

        images.stream().forEach((o)-> {
            o.setNotice(notice);
            imageRepository.save(o);
        });
        return "저장 완료";

    }

    // 전체 게시글 중 type 으로 조회
    @Transactional
    public List<GetAllNoticeResDto> inquireNotices(Long festivalId, ArticleType type) throws NotFoundException{
        List<Notice> notices = noticeRepository.findAllByFestivalAndType(festivalRepository.findById(festivalId)
                .orElseThrow(() -> new NotFoundException(festivalId + "에 해당하는 공지사항이 존재하지 않습니다.")),type);

        List<GetAllNoticeResDto> response = notices.stream()
                .map(o-> new GetAllNoticeResDto(o))
                .collect(Collectors.toList());

        return response;
    }


    // 단일 게시글 조회
    @Transactional
    public GetNoticeResDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id + "에 해당하는 공지사항이 존재하지 않습니다."));

        GetNoticeResDto response = new GetNoticeResDto(notice);
        response.setImages(notice.getImages());

        return response;


    }

    //배너 등록,해제
    @Transactional
    public String changeBanner(Long noticeId, Boolean pick) throws Exception{
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()->new Exception("실종자 정보가 존재하지 않습니다."));
        notice.setPicked(pick);
        return "반영 완료";
    }


    //공지사항 수정
    @Transactional
    public GetNoticeResDto updateNotice(Long noticeId, NoticeReqDto noticeReqDto) throws Exception {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NotFoundException("존재하지 않습니다"));
        notice.setAll(noticeReqDto);
        notice.getImages().stream().forEach(o->imageRepository.delete(o));
        List<Image> images = noticeReqDto.getImages().stream().map(o-> new Image(o)).collect(Collectors.toList());

        images.stream().forEach((o)-> {
            o.setNotice(notice);
            imageRepository.save(o);
        });


        GetNoticeResDto response = new GetNoticeResDto(notice);
        response.setImages(images);

        return response;
    }


    @Transactional
    public String deleteNotice(Long noticeId) throws Exception{
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()->new NotFoundException("존재하지 않습니다."));
        noticeRepository.delete(notice);
        return "삭제완료";
    }

    //등록된 배너 전체 조회
    public List<BannerResDto> getRegisteredBanner() {
        return noticeRepository.findAllByPicked(true).stream().map(o->new BannerResDto(o)).collect(Collectors.toList());
    }

    // 배너 순서 정하기
    @Transactional
    public String editBannerRank(List<BannerEditReqDto> bannerEditReqDtos) {
        List<Notice> notices = noticeRepository.findAllById(bannerEditReqDtos.stream().map(o->o.getNoticeId()).collect(Collectors.toList()));

        for(Notice notice : notices){
            for(BannerEditReqDto b : bannerEditReqDtos) {
                if (b.getNoticeId() == notice.getId()) {
                    notice.setRank(b.getRank());
                    break;
                }
            }
        }

        return "수정완료";
    }
}

