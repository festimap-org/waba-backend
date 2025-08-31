package com.halo.eventer.domain.stamp.service.v2;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.stamp.*;
import com.halo.eventer.domain.stamp.dto.mission.request.*;
import com.halo.eventer.domain.stamp.dto.mission.response.*;
import com.halo.eventer.domain.stamp.exception.*;
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
    public void createMission(long festivalId, long stampId, String missionName, Boolean showMission) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = Mission.from(stamp, missionName, showMission);
        missionRepository.save(mission);
    }

    @Transactional(readOnly = true)
    public MissionListResDto getMissions(long festivalId, long stampId) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        StampMissionBasicSetting setting = loadSettingOrThrow(stampId);
        List<Mission> missions = loadAllMissions(stampId);
        List<MissionBriefResDto> missionList = MissionBriefResDto.fromEntities(missions);
        return new MissionListResDto(setting.getMissionCount(), missionList);
    }

    @Transactional
    public void deleteMission(long festivalId, long stampId, long missionId) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        missionRepository.delete(mission);
    }

    @Transactional
    public void toggleMissionShowing(long festivalId, long stampId, long missionId, boolean show) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        mission.updateMissionShow(show);
    }

    @Transactional
    public StampMissionBasicSettingsResDto getBasicSettings(long festivalId, long stampId) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        StampMissionBasicSetting setting = getSettingEnsuring(stampId, stamp);
        return StampMissionBasicSettingsResDto.from(setting, stamp);
    }

    @Transactional
    public void updateBasicSettings(long festivalId, long stampId, final MissionBasicSettingsReqDto request) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        StampMissionBasicSetting setting = loadSettingOrThrow(stampId);
        setting.updateBasic(request.getMissionCount(), request.getMissionDetailsDesignLayout());
    }

    @Transactional
    public void addPrize(long festivalId, long stampId, final MissionPrizeCreateReqDto request) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        StampMissionPrize prize =
                StampMissionPrize.from(request.getRequiredCount(), request.getPrizeDescription(), stamp);
        prizeRepository.save(prize);
    }

    @Transactional
    public void updatePrize(long festivalId, long stampId, long prizeId, final MissionPrizeUpdateReqDto request) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        StampMissionPrize prize = loadStampMissionPrizeOrThrow(prizeId, stampId);
        prize.update(request.getRequiredCount(), request.getPrizeDescription());
    }

    @Transactional
    public void deletePrize(long festivalId, long stampId, long prizeId) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        StampMissionPrize prize = loadStampMissionPrizeOrThrow(prizeId, stampId);
        prizeRepository.delete(prize);
    }

    @Transactional(readOnly = true)
    public StampMissionClearImageResDto getStampMissionCompleteImage(long festivalId, long stampId, long missionId) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        return StampMissionClearImageResDto.from(
                mission.isShowTitle(), mission.getClearedThumbnail(), mission.getNotClearedThumbnail());
    }

    @Transactional
    public void updateStampMissionCompleteImage(
            long festivalId, long stampId, long missionId, final StampMissionClearImageReqDto request) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        mission.updateTitleVisible(request.isShowMissionTitle());
        mission.updateClearedImage(request.getClearedThumbnail(), request.getNotClearedThumbnail());
    }

    @Transactional(readOnly = true)
    public MissionDetailsTemplateResDto getMissionDetailsTemplate(long festivalId, long stampId, long missionId) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        MissionDetailsTemplate template = loadOrCreateMissionDetailsTemplate(mission);
        return MissionDetailsTemplateResDto.from(
                template, mission.isShowTitle(), mission.isShowRequiredSuccessCount(), mission.getTitle());
    }

    @Transactional
    public void upsertMissionDetailsTemplate(
            long festivalId, long stampId, long missionId, final MissionDetailsTemplateReqDto request) {
        Stamp stamp = ensureStamp(festivalId, stampId);
        Mission mission = loadMissionOrThrow(missionId);
        MissionDetailsTemplate template = loadMissionDetailsTemplate(mission);
        mission.updateTitle(request.getMissionTitle());
        mission.updateTitleVisible(request.isShowMissionTitle());
        mission.updateRequiredSuccessCountVisible(request.isShowSuccessCount());
        template.update(
                request.getMissionDetailsDesignLayout(),
                request.isShowExtraInfos(),
                request.isShowButtons(),
                request.getExtraInfoType(),
                request.getMediaSpec(),
                request.getMediaUrl(),
                request.getButtonLayout());
        template.replaceExtraInfos(request.getExtraInfos());
        template.updateButtons(request.getButtons());
    }

    @Transactional(readOnly = true)
    public List<MissionQrDataResDto> getMissionsQrData(long festivalId, long stampId) {
        ensureStamp(festivalId, stampId);
        List<Mission> missions = loadAllMissions(stampId);
        return MissionQrDataResDto.fromEntities(missions);
    }

    private Stamp ensureStamp(long festivalId, long stampId) {
        Stamp stamp = loadStampOrThrow(stampId);
        stamp.ensureStampInFestival(festivalId);
        return stamp;
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

    private StampMissionPrize loadStampMissionPrizeOrThrow(long prizeId, long stampId) {
        return prizeRepository
                .findByIdAndStampId(prizeId, stampId)
                .orElseThrow(() -> new MissionPrizeNotFoundException(prizeId));
    }

    private StampMissionBasicSetting loadSettingOrThrow(long stampId) {
        return settingRepository
                .findByStampId(stampId)
                .orElseThrow(() -> new StampMissionBasicSettingException(stampId));
    }

    private StampMissionBasicSetting getSettingEnsuring(long stampId, Stamp stamp) {
        return settingRepository
                .findByStampId(stampId)
                .orElseGet(() -> settingRepository.save(StampMissionBasicSetting.defaultFor(stamp)));
    }

    private Stamp loadStampOrThrow(long id) {
        return stampRepository.findById(id).orElseThrow(() -> new StampNotFoundException(id));
    }

    private List<Mission> loadAllMissions(long stampId) {
        return missionRepository.findAllByStampId(stampId);
    }
}
