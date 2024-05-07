package com.halo.eventer.concert_info;


import com.halo.eventer.festival.Festival;
import com.halo.eventer.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class ConcertInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ConcertInfoType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festivalId")
    private Festival festival;

    @OneToMany(mappedBy = "concertInfo", fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<Image> images;

    public ConcertInfo(String name,ConcertInfoType type, Festival festival) {
        this.name = name;
        this.type = type;
        this.festival = festival;
    }

    public  void setImages(List<Image> images){
        this.images = images;
        images.forEach(o->o.setConcertInfo(this));
    }
}
