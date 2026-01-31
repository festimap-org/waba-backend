package com.halo.eventer.domain.program_reservation;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.global.common.BaseTime;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(
        name = "festival_common_template",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_festival_common_template_festival_sort",
                        columnNames = {"festival_id", "sort_order"}
                )
        }
)
public class FestivalCommonTemplate extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "festival_common_template_id")
    private Long id;

    @Column(nullable = false, length = 80)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "festival_id", nullable = false)
    private Festival festival;

    public static FestivalCommonTemplate of(Festival festival, int sortOrder, String title, String content) {
        FestivalCommonTemplate b = new FestivalCommonTemplate();
        b.festival = festival;
        b.sortOrder = sortOrder;
        b.title = title;
        b.content = content;
        b.isActive = true;
        return b;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }
}

