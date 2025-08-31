package com.halo.eventer.domain.stamp.dto.mission.request;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonLayout;
import com.halo.eventer.domain.stamp.dto.stamp.enums.ExtraInfoLayout;
import com.halo.eventer.domain.stamp.dto.stamp.enums.MediaSpec;
import com.halo.eventer.domain.stamp.dto.stamp.enums.MissionDetailsDesignLayout;
import com.halo.eventer.domain.stamp.dto.stamp.request.ButtonReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionDetailsTemplateReqDto {
    @NotNull
    private MissionDetailsDesignLayout missionDetailsDesignLayout;

    @NotEmpty
    private String missionTitle;

    private boolean showMissionTitle;
    private boolean showSuccessCount;
    private boolean showExtraInfos;
    private boolean showButtons;

    @NotNull
    private MediaSpec mediaSpec;

    private String mediaUrl;

    private ExtraInfoLayout extraInfoType;
    private List<@Valid MissionExtraInfoUpdateReqDto> extraInfos;

    @NotNull
    private ButtonLayout buttonLayout;

    private List<@Valid ButtonReqDto> buttons;
}
