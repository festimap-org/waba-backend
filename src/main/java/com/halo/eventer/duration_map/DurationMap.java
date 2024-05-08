package com.halo.eventer.duration_map;


import com.halo.eventer.duration.Duration;
import com.halo.eventer.map.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class DurationMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "durationId")
    private Duration duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapId")
    private Map map;

    public DurationMap(Duration duration, Map map) {
        this.duration = duration;
        this.map = map;
    }


}

