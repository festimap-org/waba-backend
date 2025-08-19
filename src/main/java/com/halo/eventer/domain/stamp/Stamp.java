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

    private String boothAdminPassword;

    @Enumerated(EnumType.STRING)
    private AuthMethod authMethod = AuthMethod.TAG_SCAN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "stamp_notice_id")
    private StampNotice notice;

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL)
    private List<PageTemplate> templates = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL)
    private List<ParticipateGuide> participationGuides = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StampUser> stampUsers = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mission> missions = new ArrayList<>();

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

    public void changeBasicSettings(String newTitle, boolean activation, AuthMethod authMethod, String boothPassword) {
        this.title = newTitle;
        this.isActive = activation;
        this.authMethod = authMethod;
        this.boothAdminPassword = boothPassword;
    }

    public void upsertNotice(String caution, String privacy) {
        String safeCaution = (caution == null) ? "" : caution;
        String safePrivacy = (privacy == null) ? "" : privacy;
        if (this.notice == null) {
            this.notice = StampNotice.from(this, safeCaution, safePrivacy);
        } else {
            this.notice.upsert(safeCaution, safePrivacy);
        }
    }

    public void ensureStampInFestival(Festival festival) {
        if (this.festival == null || !this.festival.getId().equals(festival.getId())) {
            throw new BaseException(ErrorCode.STAMP_NOT_IN_FESTIVAL);
        }
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
