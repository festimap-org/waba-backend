package com.halo.eventer.domain.program_reservation;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.global.common.BaseTime;
import lombok.AccessLevel;
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

    private LocalDateTime activeStartAt;

    private LocalDateTime activeEndAt;

    private LocalDateTime bookingOpenAt;

    private LocalDateTime bookingCloseAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    public Program(String name) {
        this.name = name;
    }

    public void assignFestival(Festival festival) {
        this.festival = festival;
        this.isActive = false;
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
    }

    public void updateActiveStartAt(LocalDateTime activeStartAt) {
        this.activeStartAt = activeStartAt;
    }

    public void updateActiveEndAt(LocalDateTime activeEndAt) {
        this.activeEndAt = activeEndAt;
    }

    public void updateBookingOpenAt(LocalDateTime bookingOpenAt) {
        this.bookingOpenAt = bookingOpenAt;
    }

    public void updateBookingCloseAt(LocalDateTime bookingCloseAt) {
        this.bookingCloseAt = bookingCloseAt;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void update(
            String thumbnailUrl,
            PricingType pricingType,
            int priceAmount,
            String durationTime,
            String availableAge,
            PersonLimit personLimit,
            int maxPersonCount) {
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
        FREE,
        PAID
    }

    public enum PersonLimit {
        UNLIMITED,
        LIMITED
    }
}
