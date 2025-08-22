package com.halo.eventer.domain.stamp;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StampMissionPrize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "required_count", nullable = false)
    private int requiredCount;

    @Column(nullable = false, length = 1000)
    private String rewardDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setting_id", nullable = false)
    private StampMissionBasicSetting setting;

    @Builder
    private StampMissionPrize(int requiredCount, String rewardDescription, StampMissionBasicSetting setting) {
        this.requiredCount = requiredCount;
        this.rewardDescription = rewardDescription;
        this.setting = setting;
        registerSettings(setting);
    }

    private void registerSettings(StampMissionBasicSetting setting) {
        this.setting = setting;
        setting.getPrizes().add(this);
    }

    public void update(int requiredCount, String rewardDescription) {
        this.requiredCount = requiredCount;
        this.rewardDescription = rewardDescription;
    }

    public static StampMissionPrize from(
            int requiredCount, String rewardDescription, StampMissionBasicSetting setting) {
        return StampMissionPrize.builder()
                .rewardDescription(rewardDescription)
                .requiredCount(requiredCount)
                .setting(setting)
                .build();
    }
}
