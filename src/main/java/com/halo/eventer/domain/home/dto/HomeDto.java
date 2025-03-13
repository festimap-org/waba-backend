package com.halo.eventer.domain.home.dto;

import com.halo.eventer.domain.down_widget.dto.DownWidgetDto;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.MainMenuDto;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerHomeResDto;
import com.halo.eventer.domain.missing_person.MissingPerson;
import com.halo.eventer.domain.missing_person.dto.MissingPersonPopupDto;
import com.halo.eventer.domain.widget.dto.square_widget.SquareWidgetResDto;
import com.halo.eventer.domain.widget.dto.up_widget.UpWidgetResDto;
import com.halo.eventer.domain.widget.entity.UpWidget;
import com.halo.eventer.domain.notice.dto.RegisteredBannerGetDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeDto {
    // todo: List<RegisteredBannerGetDto> -> RegisteredBannerGetListDto
    private List<UpWidgetResDto> upWidgets;
    private List<RegisteredBannerGetDto> banner;
    private MainMenuDto mainMenuDto;
    private List<SquareWidgetResDto> widgetDto;
    private List<DownWidgetDto> downWidgetDtos;
    private List<MissingPersonPopupDto> missingPersonDtos;
    private List<MiddleBannerHomeResDto> middleBannerDtos;

    public HomeDto(List<RegisteredBannerGetDto> banner, Festival festival, List<UpWidget> upWidgets, List<MissingPerson> missingPersons) {
        //this.upWidgets =upWidgets.stream().map(UpWidgetrResDto::new).collect(Collectors.toList());
        this.banner = banner;
        this.mainMenuDto = MainMenuDto.from(festival);
        //this.widgetDto = festival.getWidgets().stream().map(WidgetDto::new).collect(Collectors.toList());
        this.downWidgetDtos = festival.getDownWidget1s().stream().map(DownWidgetDto::new).collect(Collectors.toList());
        this.missingPersonDtos = missingPersons.stream().map(MissingPersonPopupDto::new).collect(Collectors.toList());
        this.middleBannerDtos = festival.getMiddleBanners().stream().map(MiddleBannerHomeResDto::new).collect(Collectors.toList());
    }
}
