package com.halo.eventer.domain.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.*;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.stamp.dto.stamp.enums.AuthMethod;
import com.halo.eventer.domain.stamp.exception.StampClosedException;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private boolean isActive = true;

    @Column(nullable = false)
    private int finishCount = 0;

    private String prizeReceiptAuthPassword;

    private boolean showStamp = true;

    @Enumerated(EnumType.STRING)
    private AuthMethod authMethod = AuthMethod.TAG_SCAN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StampNotice> stampNotices = new ArrayList<>();

    @OneToOne(mappedBy = "stamp", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private StampMissionBasicSetting basicSetting;

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL)
    private List<PageTemplate> templates = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL)
    private List<ParticipateGuide> participationGuides = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StampUser> stampUsers = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("requiredCount ASC")
    private List<StampMissionPrize> prizes = new ArrayList<>();

    private Stamp(Festival festival) {
        this.festival = festival;
        festival.getStamps().add(this);
    }

    public Stamp(String title) {
        this.title = title;
    }

    public void assignAllMissionsTo(StampUser stampUser) {
        List<UserMission> userMissions =
                missions.stream().map(m -> UserMission.create(m, stampUser)).collect(Collectors.toList());
        stampUser.assignUserMissions(userMissions);
    }

    public void switchActivation() {
        isActive = !isActive;
    }

    public void defineFinishCnt(int cnt) {
        this.finishCount = cnt;
    }

    public void validateActivation() {
        if (!this.isActive()) {
            throw new StampClosedException(id);
        }
    }

    private void registerTo(Festival festival) {
        this.festival = festival;
        festival.getStamps().add(this);
    }

    public void registerMissionBasicSetting(StampMissionBasicSetting setting) {
        this.basicSetting = setting;
    }

    public void changeBasicSettings(
            boolean activation, String newTitle, AuthMethod authMethod, String prizeReceiptAuthPassword) {
        this.title = newTitle;
        this.isActive = activation;
        this.authMethod = authMethod;
        this.prizeReceiptAuthPassword = prizeReceiptAuthPassword;
    }

    public void ensureStampInFestival(long festivalId) {
        if (this.festival == null || !this.festival.getId().equals(festivalId)) {
            throw new BaseException(ErrorCode.STAMP_NOT_IN_FESTIVAL);
        }
    }

    public void changeShowStamp(boolean show) {
        this.showStamp = show;
    }

    public static Stamp create(Festival festival) {
        return new Stamp(festival);
    }

    public static Stamp createWith(Festival festival, String title) {
        Stamp stamp = new Stamp(title);
        stamp.registerTo(festival);
        return stamp;
    }
}
