package com.halo.eventer.domain.stamp.service.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.stamp.*;
import com.halo.eventer.domain.stamp.dto.stamp.enums.PageType;
import com.halo.eventer.domain.stamp.dto.stamp.request.*;
import com.halo.eventer.domain.stamp.dto.stamp.response.*;
import com.halo.eventer.domain.stamp.exception.ParticipateGuideNotFoundException;
import com.halo.eventer.domain.stamp.exception.ParticipateGuidePageNotFoundException;
import com.halo.eventer.domain.stamp.exception.StampNotFoundException;
import com.halo.eventer.domain.stamp.repository.PageTemplateRepository;
import com.halo.eventer.domain.stamp.repository.ParticipateGuidePageRepository;
import com.halo.eventer.domain.stamp.repository.ParticipateGuideRepository;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.global.common.dto.OrderUpdateRequest;
import com.halo.eventer.global.util.DisplayOrderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StampTourAdminService {

    private final FestivalRepository festivalRepository;
    private final StampRepository stampRepository;
    private final PageTemplateRepository pageTemplateRepository;
    private final ParticipateGuideRepository participateGuideRepository;
    private final ParticipateGuidePageRepository participateGuidePageRepository;

    @Transactional
    public void createStampTourByFestival(long festivalId, String title) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = Stamp.createWith(festival, title);
        stampRepository.save(stamp);
    }

    @Transactional(readOnly = true)
    public List<StampTourSummaryResDto> getStampTourListByFestival(long festivalId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        return StampTourSummaryResDto.from(festival.getStamps());
    }

    @Transactional(readOnly = true)
    public StampTourSettingBasicResDto getStampTourSettingBasicByFestival(long festivalId, long stampId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        return StampTourSettingBasicResDto.from(stamp);
    }

    @Transactional
    public void updateBasicSettings(long festivalId, long stampId, final StampTourBasicUpdateReqDto request) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        stamp.changeBasicSettings(
                request.getNewTitle(), request.isActivation(), request.getAuthMethod(), request.getBoothPassword());
    }

    @Transactional(readOnly = true)
    public StampTourNotificationResDto getStampTourNotification(long festivalId, long stampId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        StampNotice stampNotice = stamp.getNotice();
        return StampTourNotificationResDto.from(stampNotice);
    }

    @Transactional
    public void updateStampTourNotification(
            long festivalId, long stampId, String cautionContent, String personalInformationContent) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        stamp.upsertNotice(cautionContent, personalInformationContent);
    }

    @Transactional
    public StampTourLandingPageResDto getLandingPageSettings(long festivalId, long stampId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        PageTemplate pageTemplate = pageTemplateRepository
                .findFirstByStampIdAndType(stampId, PageType.LANDING)
                .orElseGet(() -> createNewLandingTemplate(stamp));
        return StampTourLandingPageResDto.from(pageTemplate);
    }

    @Transactional
    public void updateLandingPage(long festivalId, long stampId, StampTourLandingPageReqDto request) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        PageTemplate pageTemplate = pageTemplateRepository
                .findFirstByStampIdAndType(stampId, PageType.LANDING)
                .orElseGet(() -> createNewLandingTemplate(stamp));
        pageTemplate.updateLandingPageTemplate(request);
    }

    @Transactional
    public StampTourMainPageResDto getMainPageSettings(long festivalId, long stampId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        PageTemplate pageTemplate = pageTemplateRepository
                .findFirstByStampIdAndType(stampId, PageType.MAIN)
                .orElseGet(() -> createNewMainTemplate(stamp));
        return StampTourMainPageResDto.from(pageTemplate);
    }

    @Transactional
    public void updateMainPageSettings(long festivalId, long stampId, StampTourMainPageReqDto request) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        PageTemplate pageTemplate = pageTemplateRepository
                .findFirstByStampIdAndType(stampId, PageType.MAIN)
                .orElseGet(() -> createNewMainTemplate(stamp));
        pageTemplate.updateMainPageTemplate(request);
    }

    @Transactional
    public StampTourParticipateGuideResDto getParticipateGuide(long festivalId, long stampId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuide guide = participateGuideRepository
                .findFirstByStampId(stampId)
                .orElseGet(() -> createNewParticipateGuide(stamp));
        return StampTourParticipateGuideResDto.from(guide);
    }

    @Transactional
    public void upsertParticipateGuide(long festivalId, long stampId, StampTourParticipateGuideReqDto request) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuide guide = loadParticipateGuideOrThrow(stampId);
        guide.update(request.getTemplate(), request.getMethod());
    }

    @Transactional
    public void deleteParticipateGuidePage(long festivalId, long stampId, long pageId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuidePage page = loadParticipateGuidePageOrThrow(pageId);
        participateGuidePageRepository.delete(page);
    }

    @Transactional
    public void createParticipateGuidePage(long festivalId, long stampId, StampTourParticipateGuidePageReqDto request) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuide guide = loadParticipateGuideOrThrow(stampId);
        ParticipateGuidePage.from(
                request.getTitle(),
                request.getGuideMediaSpec(),
                request.getMediaUrl(),
                request.getSummary(),
                request.getDetails(),
                request.getAdditional(),
                guide);
    }

    @Transactional(readOnly = true)
    public ParticipateGuidePageDetailsResDto getParticipateGuidePageDetails(
            long festivalId, long stampId, long pageId) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuidePage page = loadParticipateGuidePageOrThrow(pageId);
        return ParticipateGuidePageDetailsResDto.from(page);
    }

    @Transactional
    public void updateParticipateGuidePage(
            long festivalId, long stampId, long pageId, StampTourParticipateGuidePageReqDto request) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuidePage page = loadParticipateGuidePageOrThrow(pageId);
        page.update(request);
    }

    @Transactional
    public List<ParticipateGuidePageSummaryResDto> updateDisplayOrder(
            long festivalId, long stampId, List<OrderUpdateRequest> orderRequests) {
        Festival festival = loadFestivalOrThrow(festivalId);
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festival);
        ParticipateGuide guide = loadParticipateGuideOrThrow(stampId);
        List<ParticipateGuidePage> pages = guide.getParticipateGuidePages();
        DisplayOrderUtils.updateDisplayOrder(pages, orderRequests);
        return pages.stream().map(ParticipateGuidePageSummaryResDto::from).collect(Collectors.toList());
    }

    private PageTemplate createNewLandingTemplate(Stamp stamp) {
        PageTemplate newPageTemplate = PageTemplate.defaultLandingPage(stamp);
        return pageTemplateRepository.save(newPageTemplate);
    }

    private PageTemplate createNewMainTemplate(Stamp stamp) {
        PageTemplate newPageTemplate = PageTemplate.defaultMainPage(stamp);
        return pageTemplateRepository.save(newPageTemplate);
    }

    private ParticipateGuide createNewParticipateGuide(Stamp stamp) {
        ParticipateGuide participationGuide = ParticipateGuide.defaultParticipateGuide(stamp);
        return participateGuideRepository.save(participationGuide);
    }

    private Festival loadFestivalOrThrow(long id) {
        return festivalRepository.findById(id).orElseThrow(() -> new FestivalNotFoundException(id));
    }

    private Stamp loadStampOrThrow(long stampId) {
        return stampRepository.findById(stampId).orElseThrow(() -> new StampNotFoundException(stampId));
    }

    private ParticipateGuide loadParticipateGuideOrThrow(long stampId) {
        return participateGuideRepository
                .findFirstByStampId(stampId)
                .orElseThrow(() -> new ParticipateGuideNotFoundException(stampId));
    }

    private ParticipateGuidePage loadParticipateGuidePageOrThrow(long pageId) {
        return participateGuidePageRepository
                .findById(pageId)
                .orElseThrow(() -> new ParticipateGuidePageNotFoundException(pageId));
    }
}
