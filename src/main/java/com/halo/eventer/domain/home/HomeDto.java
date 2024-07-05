package com.halo.eventer.domain.home;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.MainMenuDto;
import com.halo.eventer.domain.festival.dto.MiddleBannerDto;
import com.halo.eventer.domain.notice.dto.BannerResDto;
import com.halo.eventer.domain.widget.dto.WidgetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class HomeDto {
    private List<BannerResDto> banner;
    private MainMenuDto mainMenuDto;
    private List<WidgetDto> widgetDto;
    private MiddleBannerDto middleBannerDto;

    public HomeDto(List<BannerResDto> banner, Festival festival) {
        this.banner = banner;
        this.mainMenuDto = new MainMenuDto(festival);
        this.widgetDto = festival.getWidgets().stream().map(WidgetDto::new).collect(Collectors.toList());
        this.middleBannerDto = new MiddleBannerDto(festival);
    }
}
