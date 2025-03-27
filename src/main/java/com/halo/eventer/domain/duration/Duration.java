package com.halo.eventer.domain.duration;



import com.halo.eventer.domain.duration.dto.DurationCreateDto;
import com.halo.eventer.domain.festival.Festival;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Festival festival;

    @OneToMany(mappedBy = "duration",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<DurationMap> durationMaps = new ArrayList<>();

    public Duration(DurationCreateDto durationDto, Festival festival) {
        this.date = durationDto.getDate();
        this.day = durationDto.getDay();
        this.festival = festival;
    }
}
