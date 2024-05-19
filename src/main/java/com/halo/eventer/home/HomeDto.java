package com.halo.eventer.home;

import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.dto.MainMenuDto;
import com.halo.eventer.notice.dto.BannerResDto;
import com.halo.eventer.user.dto.MissingPersonPopupDto;
import com.halo.eventer.user.dto.UrgentDto;
import com.halo.eventer.widget.dto.WidgetDto;
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

    public HomeDto(List<BannerResDto> banner, Festival festival) {
        this.banner = banner;
        this.mainMenuDto = new MainMenuDto(festival);
        this.widgetDto = festival.getWidgets().stream().map(WidgetDto::new).collect(Collectors.toList());
    }
}
