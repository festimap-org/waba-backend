package com.halo.eventer.domain.duration;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.duration.dto.DurationCreateDto;
import com.halo.eventer.domain.duration_map.DurationMap;
import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Duration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Integer day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festivalId")
    @JsonBackReference
    private Festival festival;

    @OneToMany(mappedBy = "duration",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<DurationMap> durationMaps = new ArrayList<>();

    @OneToMany(mappedBy = "duration",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Concert> concerts = new ArrayList<>();

    public Duration(DurationCreateDto durationDto, Festival festival) {
        this.date = durationDto.getDate();
        this.day = durationDto.getDay();
        this.festival = festival;
    }
}
