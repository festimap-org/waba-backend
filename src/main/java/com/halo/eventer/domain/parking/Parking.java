package com.halo.eventer.domain.parking;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.parking.dto.parking.ParkingAnnouncementReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "announcement", nullable = true, length = 500)
    private String announcement;

    @Column(name = "is_announcement_enabled")
    private boolean isAnnouncementEnabled = false;

    @Column(name = "is_congestion_enabled")
    private boolean isCongestionEnabled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "parking", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<ParkingPlace> parkingPlaces = new ArrayList<>();

    public Parking(Festival festival) {
        this.festival = festival;
    }

    public void updateAnnouncement(ParkingAnnouncementReqDto parkingAnnouncementReqDto){
        this.announcement = parkingAnnouncementReqDto.getAnnouncement();
        this.isAnnouncementEnabled = parkingAnnouncementReqDto.isAnnouncementEnabled();
    }

    public void updateIsCongestionEnabled(boolean isCongestionEnabled){
        this.isCongestionEnabled = isCongestionEnabled;
    }
}
