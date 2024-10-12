package com.halo.eventer.domain.festival;


import com.halo.eventer.domain.concert_info.ConcertInfo;
import com.halo.eventer.domain.down_widget.DownWidget;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.duration.Duration;
import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.inquiry.Inquiry;
import com.halo.eventer.domain.lost_item.LostItem;
import com.halo.eventer.domain.manager.Manager;
import com.halo.eventer.domain.map.MapCategory;
import com.halo.eventer.domain.middle_banner.MiddleBanner;
import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.domain.splash.Splash;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.up_widget.UpWidget;
import com.halo.eventer.domain.widget.Widget;
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
    private String subAddress;

    //4가지 커스텀 색
    private String mainColor;
    private String subColor;
    private String fontColor;
    private String backgroundColor;

    private String logo; //메인 페이지 최상단에 보여지는 이미지

    //매인 배너 아래 2개의 컴포넌트 관련 필드
    private String menuName1;
    private String menuImage1;
    private String menuSummary1;
    private String menuUrl1;

    private String menuName2;
    private String menuImage2;
    private String menuSummary2;
    private String menuUrl2;

    //2번째 컴포넌트에 들어갔을 때 나옴
    private String entrySummary;
    private String entryIcon;
    private String viewSummary;
    private String viewIcon;

    // 축제 위치 정보
    private double latitude; // 위도
    private double longitude; // 경도



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

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UpWidget> upWidgets = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DownWidget> downWidgets = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inquiry> inquiries = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Stamp> stamps = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LostItem> lostItems = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Manager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Splash> splashes = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MiddleBanner> middleBanners = new ArrayList<>();

    @Builder
    public Festival(FestivalCreateDto festivalCreateDto) {
        this.name = festivalCreateDto.getName();
        this.subAddress = festivalCreateDto.getSubAddress();
    }

    public void setFestival(FestivalCreateDto festivalCreateDto) {
        this.name = festivalCreateDto.getName();
        this.subAddress = festivalCreateDto.getSubAddress();
    }

    public void setColor(ColorReqDto colorReqDto) {
        this.mainColor = colorReqDto.getMainColor();
        this.subColor = colorReqDto.getSubColor();
        this.fontColor = colorReqDto.getFontColor();
        this.backgroundColor = colorReqDto.getBackgroundColor();
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setMainMenu(MainMenuDto mainMenuDto) {
        this.menuName1 = mainMenuDto.getMenuName1();
        this.menuName2 = mainMenuDto.getMenuName2();
        this.menuSummary1 = mainMenuDto.getMenuSummary1();
        this.menuSummary2 = mainMenuDto.getMenuSummary2();
        this.menuImage1 = mainMenuDto.getMenuImage1();
        this.menuImage2 = mainMenuDto.getMenuImage2();
        this.menuUrl1 = mainMenuDto.getMenuUrl1();
        this.menuUrl2 = mainMenuDto.getMenuUrl2();
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

    public void updateLocation(FestivalLocationDto festivalLocationDto) {
        this.longitude = festivalLocationDto.getLongitude();
        this.latitude = festivalLocationDto.getLatitude();
    }
}
