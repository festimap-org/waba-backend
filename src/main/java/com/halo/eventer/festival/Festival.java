package com.halo.eventer.festival;


import com.halo.eventer.festival.dto.FestivalCreateDto;
import com.halo.eventer.concert.Concert;
import com.halo.eventer.map.Map;
import com.halo.eventer.notice.Notice;
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

    @Column(columnDefinition = "varchar(2000)")
    private String content;

    private String image;

    private String location;



    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Map> maps = new ArrayList<>();


    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Concert> concerts = new ArrayList<>();


    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Notice> mapCategories = new ArrayList<>();


    @Builder
    public Festival(FestivalCreateDto festivalCreateDto) {
        this.name = festivalCreateDto.getName();
        this.content = festivalCreateDto.getContent();
        this.image = festivalCreateDto.getImage();
        this.location = festivalCreateDto.getLocation();
    }

    public void setFestival(FestivalCreateDto festivalCreateDto) {
        this.name = festivalCreateDto.getName();
        this.content = festivalCreateDto.getContent();
        this.image = festivalCreateDto.getImage();
        this.location = festivalCreateDto.getLocation();
    }
}
