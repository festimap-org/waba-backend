package com.halo.eventer.domain.stamp.service.v2;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.stamp.*;
import com.halo.eventer.domain.stamp.dto.mission.request.*;
import com.halo.eventer.domain.stamp.dto.mission.response.*;
import com.halo.eventer.domain.stamp.exception.MissionDetailsTemplateNotFoundException;
import com.halo.eventer.domain.stamp.exception.MissionNotFoundException;
import com.halo.eventer.domain.stamp.exception.MissionPrizeNotFoundException;
import com.halo.eventer.domain.stamp.exception.StampNotFoundException;
import com.halo.eventer.domain.stamp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StampMissionAdminService {

    private final StampRepository stampRepository;
    private final StampMissionBasicSettingRepository settingRepository;
    private final StampMissionPrizeRepository prizeRepository;
    private final MissionRepository missionRepository;
    private final MissionDetailsTemplateRepository templateRepository;
    private final MissionExtraInfoRepository extraInfoRepository;

    @Transactional
    public void createMission(long festivalId, long stampId, String missionName) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festivalId);
        Mission mission = Mission.from(stamp, missionName);
        missionRepository.save(mission);
    }

    @Transactional(readOnly = true)
    public List<MissionBriefResDto> getMissions(long festival, long stampId) {
        ensureStamp(festival, stampId);
        List<Mission> missions = missionRepository.findAllByStampId(stampId);
        return MissionBriefResDto.fromEntities(missions);
    }

    @Transactional(readOnly = true)
    public StampMissionBasicSettingsResDto getBasicSettings(long festivalId, long stampId) {
        StampMissionBasicSetting setting = getSettingEnsuring(festivalId, stampId);
        return StampMissionBasicSettingsResDto.from(setting);
    }

    @Transactional
    public void upsertBasicSettings(long festivalId, long stampId, final MissionBasicSettingsReqDto request) {
        StampMissionBasicSetting setting = getSettingEnsuring(festivalId, stampId);
        setting.upsertBasic(request.getMissionCount(), request.getDefaultDetailLayout());
    }

    @Transactional
    public MissionPrizeResDto addPrize(long festivalId, long stampId, final MissionPrizeCreateReqDto request) {
        StampMissionBasicSetting setting = getSettingEnsuring(festivalId, stampId);
        StampMissionPrize prize =
                StampMissionPrize.from(request.getRequiredCount(), request.getPrizeDescription(), setting);
        prizeRepository.save(prize);
        return MissionPrizeResDto.from(prize);
    }

    @Transactional
    public void updatePrize(long festivalId, long stampId, long prizeId, final MissionPrizeUpdateReqDto request) {
        StampMissionBasicSetting setting = getSettingEnsuring(festivalId, stampId);
        StampMissionPrize prize = loadStampMissionPrizeOrThrow(prizeId);
        prize.update(request.getRequiredCount(), request.getPrizeDescription());
    }

    @Transactional
    public void deletePrize(long festivalId, long stampId, long prizeId) {
        StampMissionBasicSetting setting = getSettingEnsuring(festivalId, stampId);
        StampMissionPrize prize = loadStampMissionPrizeOrThrow(prizeId);
        prizeRepository.delete(prize);
    }

    @Transactional(readOnly = true)
    public StampMissionClearImageResDto getStampMissionCompleteImage(long festivalId, long stampId, long missionId) {
        ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        return StampMissionClearImageResDto.from(
                mission.isShowTitle(), mission.getClearedThumbnail(), mission.getNotClearedThumbnail());
    }

    @Transactional
    public void updateStampMissionCompleteImage(
            long festivalId, long stampId, long missionId, final StampMissionClearImageReqDto request) {
        ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        mission.updateTitleVisible(request.isShowMissionName());
        mission.updateClearedImage(request.getClearedThumbnail(), request.getNotClearedThumbnail());
    }

    @Transactional(readOnly = true)
    public MissionDetailsTemplateResDto getMissionDetailsTemplate(long festivalId, long stampId, long missionId) {
        ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        MissionDetailsTemplate template = loadOrCreateMissionDetailsTemplate(mission);
        return MissionDetailsTemplateResDto.from(
                template, mission.isShowTitle(), mission.isShowRequiredSuccessCount(), mission.getTitle());
    }

    @Transactional
    public void upsertMissionDetailsTemplate(
            long festivalId, long stampId, long missionId, final MissionDetailsTemplateReqDto request) {
        ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        MissionDetailsTemplate template = loadMissionDetailsTemplate(mission);
        mission.updateTitle(request.getMissionTitle());
        mission.updateTitleVisible(request.isShowMissionName());
        mission.updateRequiredSuccessCountVisible(request.isShowSuccessCount());
        template.update(
                request.getLayout(),
                request.isShowExtraInfos(),
                request.isShowButtons(),
                request.getExtraInfoType(),
                request.getMissionMediaSpec(),
                request.getMediaUrl(),
                request.getButtonLayout());
        template.replaceExtraInfos(request.getExtraInfos());
        template.updateButtons(request.getButtons());
    }

    @Transactional(readOnly = true)
    public List<MissionQrDataResDto> getMissionsQrData(long festivalId, long stampId) {
        ensureStamp(festivalId, stampId);
        List<Mission> missions = missionRepository.findAllByStampId(stampId);
        return MissionQrDataResDto.fromEntities(missions);
    }

    private void ensureStamp(long festivalId, long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festivalId);
    }

    private MissionDetailsTemplate loadOrCreateMissionDetailsTemplate(Mission mission) {
        return templateRepository
                .findByMissionId(mission.getId())
                .orElseGet(() -> MissionDetailsTemplate.defaultTemplate(mission));
    }

    private MissionDetailsTemplate loadMissionDetailsTemplate(Mission mission) {
        return templateRepository
                .findByMissionId(mission.getId())
                .orElseThrow(() -> new MissionDetailsTemplateNotFoundException(mission.getId()));
    }

    private Mission loadMissionOrThrow(long missionId) {
        return missionRepository.findById(missionId).orElseThrow(() -> new MissionNotFoundException(missionId));
    }

    private StampMissionPrize loadStampMissionPrizeOrThrow(long prizeId) {
        return prizeRepository.findById(prizeId).orElseThrow(() -> new MissionPrizeNotFoundException(prizeId));
    }

    private StampMissionBasicSetting getSettingEnsuring(long festivalId, long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festivalId);
        return settingRepository
                .findByStampId(stampId)
                .orElseGet(() -> settingRepository.save(StampMissionBasicSetting.defaultFor(stamp)));
    }

    private Stamp loadStampOrThrow(long id) {
        return stampRepository.findById(id).orElseThrow(() -> new StampNotFoundException(id));
    }
}
