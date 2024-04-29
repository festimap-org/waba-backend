package com.halo.eventer.home;

import com.halo.eventer.notice.dto.BannerResDto;
import com.halo.eventer.user.dto.MissingPersonPopupDto;
import com.halo.eventer.user.dto.UrgentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HomeDto {
    private List<BannerResDto> banner;
    private List<MissingPersonPopupDto> missingPerson;
    private List<UrgentDto> urgent;

    public HomeDto(List<BannerResDto> banner, List<MissingPersonPopupDto> missingPerson, List<UrgentDto> urgent) {
        this.banner = banner;
        this.missingPerson = missingPerson;
        this.urgent = urgent;
    }
}
