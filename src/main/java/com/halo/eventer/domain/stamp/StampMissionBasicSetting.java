package com.halo.eventer.domain.stamp;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

import com.halo.eventer.domain.stamp.dto.stamp.enums.MissionDetailsDesignLayout;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StampMissionBasicSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int missionCount = 0; // 미션개수

    @Enumerated(EnumType.STRING)
    private MissionDetailsDesignLayout defaultDetailLayout = MissionDetailsDesignLayout.CARD;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stamp_id", nullable = false, unique = true)
    private Stamp stamp;

    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("requiredCount ASC")
    private List<StampMissionPrize> prizes = new ArrayList<>();

    @Builder
    private StampMissionBasicSetting(Stamp stamp) {
        this.stamp = stamp;
        stamp.registerMissionBasicSetting(this);
    }

    public void upsertBasic(int missionCount, MissionDetailsDesignLayout layout) {
        this.missionCount = missionCount;
        this.defaultDetailLayout = layout;
    }

    public static StampMissionBasicSetting defaultFor(Stamp stamp) {
        return StampMissionBasicSetting.builder().stamp(stamp).build();
    }
}
