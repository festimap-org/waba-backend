package com.halo.eventer.domain.concert_info;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.halo.eventer.domain.concert_info.dto.ConcertInfoUpdateDto;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
public class ConcertInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String summary;

    @Enumerated(EnumType.STRING)
    private ConcertInfoType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "festivalId")
    private Festival festival;

    @OneToMany(mappedBy = "concertInfo", fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @JsonManagedReference
    private List<Image> images;


    public ConcertInfo(String name,ConcertInfoType type, Festival festival) {
        this.name = name;
        this.type = type;
        this.festival = festival;
    }

    public  void setInfo(ConcertInfoUpdateDto dto){
        this.summary = dto.getSummary();
        this.images =  dto.getImages().stream().map(Image::new).collect(Collectors.toList());
        images.forEach(o->o.setConcertInfo(this));
    }
}
