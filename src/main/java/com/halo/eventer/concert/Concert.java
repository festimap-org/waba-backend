package com.halo.eventer.concert;


import com.halo.eventer.duration.Duration;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "durationId")
    private Duration duration;


    @OneToMany(mappedBy = "concert", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Image> images = new ArrayList<>();

    public Concert(String thumbnail, Festival festival, Duration duration) {

        this.thumbnail = thumbnail;
        this.festival = festival;
        this.duration = duration;
    }

    public void setAll(String thumbnail, Duration duration){
        ;this.thumbnail = thumbnail;
        this.duration = duration;
    }

    public  void setImages(List<Image> images){
        this.images = images;
        images.forEach(o->o.setConcert(this));
    }



}
