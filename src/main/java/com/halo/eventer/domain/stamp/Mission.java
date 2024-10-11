package com.halo.eventer.domain.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_stamp_id", columnList = "stamp_id")
})
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boothId;

    private String title;

    private String content;

    private String place;

    private String time;

    private String clearedThumbnail;

    private String notClearedThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stamp_id")
    private Stamp stamp;

    public Mission(Long boothId, String title, String content, String place, String time, String clearedThumbnail, String notClearedThumbnail, Stamp stamp) {
        this.boothId = boothId;
        this.title = title;
        this.content = content;
        this.place = place;
        this.time = time;
        this.clearedThumbnail = clearedThumbnail;
        this.notClearedThumbnail = notClearedThumbnail;
        this.stamp = stamp;
    }
}
