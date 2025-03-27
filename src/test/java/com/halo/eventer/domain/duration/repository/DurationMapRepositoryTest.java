package com.halo.eventer.domain.duration.repository;

import com.halo.eventer.domain.duration.Duration;
import com.halo.eventer.domain.duration.DurationMap;
import com.halo.eventer.domain.map.Map;
import com.halo.eventer.domain.map.repository.MapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Disabled()
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
@EntityScan(basePackages = "com.halo.eventer.domain")
public class DurationMapRepositoryTest {

    @Autowired
    private DurationMapRepository durationMapRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private DurationRepository durationRepository;

    private DurationMap durationMap;
    private Duration duration;

    @BeforeEach
    void setUp() {
        Map map = new Map();
        mapRepository.save(map);
        Duration duration1 = new Duration();
        duration = durationRepository.save(duration1);
        durationMapRepository.save(new DurationMap(duration, map));
        durationMapRepository.save(new DurationMap(duration, map));
    }

    @Test
    void durationId로_DurationMap_전체조회(){
        //when
        List<DurationMap> results = durationMapRepository.findAllByDuration_IdIn(List.of(duration.getId()));

        // then
        assertThat(results).hasSize(2);
  }
}