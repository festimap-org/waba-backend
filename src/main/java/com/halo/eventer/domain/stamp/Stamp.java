package com.halo.eventer.domain.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.*;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.stamp.exception.StampClosedException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive = true;

    @Column(nullable = false)
    private int finishCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "stamp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StampUser> stampUsers = new ArrayList<>();

    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mission> missions = new ArrayList<>();

    private Stamp(Festival festival) {
        this.festival = festival;
        festival.getStamps().add(this);
    }

    public void assignAllMissionsTo(StampUser stampUser) {
        // TODO : 할당할 미션이 없는 경우를 위한 예외 정의
        if (missions.isEmpty()) {
            throw new IllegalStateException("등록된 미션이 없습니다.");
        }
        List<UserMission> userMissions = missions.stream()
                .map(mission -> UserMission.create(mission, stampUser))
                .collect(Collectors.toList());
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

    public static Stamp create(Festival festival) {
        return new Stamp(festival);
    }
}
