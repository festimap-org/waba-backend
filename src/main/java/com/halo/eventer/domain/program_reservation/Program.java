package com.halo.eventer.domain.program_reservation;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private PricingType pricingType;

    private int priceAmount;

    private String durationTime;

    private String availableAge;

    @Enumerated(EnumType.STRING)
    private PersonLimit personLimit;

    private int maxPersonCount;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    public Program(String name) {
        this.name = name;
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
    }

    public void update(String name, String thumbnailUrl, PricingType pricingType, int priceAmount,
                       String durationTime, String availableAge, PersonLimit personLimit, int maxPersonCount) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.pricingType = pricingType;
        this.priceAmount = priceAmount;
        this.durationTime = durationTime;
        this.availableAge = availableAge;
        this.personLimit = personLimit;
        this.maxPersonCount = maxPersonCount;
    }


    @Getter
    public enum PricingType {
        FREE, PAID
    }

    public enum PersonLimit {
        UNLIMITED, LIMITED
    }
}
