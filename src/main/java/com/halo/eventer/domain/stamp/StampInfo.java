package com.halo.eventer.domain.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class StampInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean mission1;
    private boolean mission2;
    private boolean mission3;
    private boolean mission4;
    private boolean mission5;
    private boolean mission6;

    private boolean finished;

    private int participantCount;

    @OneToOne
    @JoinColumn(name = "stamp_id")
    private Stamp stamp;

    public StampInfo(Stamp stamp) {
        this.stamp = stamp;
        this.mission1 = false;
        this.mission2 = false;
        this.mission3 = false;
        this.mission4 = false;
        this.mission5 = false;
        this.mission6 = false;
        this.finished = false;
        this.participantCount = 0;
    }

    public void updateMission1() { this.mission1 = true; }

    public void updateMission2() { this.mission2 = true; }

    public void updateMission3() { this.mission3 = true; }

    public void updateMission4() { this.mission4 = true; }

    public void updateMission5() { this.mission5 = true; }

    public void updateMission6() { this.mission6 = true; }

    public void setFinished() { this.finished = true; }

    public void setParticipantCount(int participantCount) { this.participantCount = participantCount; }
}
