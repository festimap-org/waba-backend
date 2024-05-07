package com.halo.eventer.festival;


import com.halo.eventer.concert_info.ConcertInfo;
import com.halo.eventer.festival.dto.FestivalConcertMenuDto;
import com.halo.eventer.duration.Duration;
import com.halo.eventer.festival.dto.ColorReqDto;
import com.halo.eventer.festival.dto.FestivalCreateDto;
import com.halo.eventer.concert.Concert;
import com.halo.eventer.festival.dto.MainMenuDto;
import com.halo.eventer.map.MapCategory;
import com.halo.eventer.notice.Notice;
import com.halo.eventer.widget.Widget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mainColor;
    private String subColor;
    private String fontColor;

    private String logo;

    private String menuName1;
    private String menuName2;
    private String menuImage1;
    private String menuImage2;

    private String entrySummary;
    private String entryIcon;
    private String viewSummary;
    private String viewIcon;


    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Concert> concerts = new ArrayList<>();


    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<MapCategory> mapCategories = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Duration> durations = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Widget> widgets = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<ConcertInfo> concertInfos = new ArrayList<>();


    @Builder
    public Festival(FestivalCreateDto festivalCreateDto) {
        this.name = festivalCreateDto.getName();

    }

    public void setFestival(FestivalCreateDto festivalCreateDto) {
        this.name = festivalCreateDto.getName();
    }

    public void setColor(ColorReqDto colorReqDto) {
        this.mainColor = colorReqDto.getMainColor();
        this.subColor = colorReqDto.getSubColor();
        this.fontColor = colorReqDto.getFontColor();
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setMainMenu(MainMenuDto mainMenuDto) {
        this.menuName1 = mainMenuDto.getMenuName1();
        this.menuName2 = mainMenuDto.getMenuName2();
        this.menuImage1 = mainMenuDto.getMenuImage1();
        this.menuImage2 = mainMenuDto.getMenuImage2();
    }

    public void setMapCategory(List<MapCategory> mapCategories) {
        this.mapCategories = mapCategories;
        mapCategories.forEach(o->o.setFestival(this));
    }

    public void setEntry(FestivalConcertMenuDto festivalConcertMenuDto){
        this.entrySummary = festivalConcertMenuDto.getSummary();
        this.entryIcon = festivalConcertMenuDto.getIcon();
    }

    public void setView(FestivalConcertMenuDto festivalConcertMenuDto){
        this.viewSummary = festivalConcertMenuDto.getSummary();
        this.viewIcon = festivalConcertMenuDto.getIcon();
    }
}
