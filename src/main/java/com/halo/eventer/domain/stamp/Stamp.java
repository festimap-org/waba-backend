package com.halo.eventer.domain.stamp;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "stamp", indexes = {@Index(name = "idx_uuid", columnList = "uuid")})
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    private String userInfo;        // name + phone

    private boolean mission1;
    private boolean mission2;
    private boolean mission3;
    private boolean mission4;
    private boolean mission5;
    private boolean mission6;

    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    public Stamp(String encryptedUserInfo) {
        this.uuid = UUID.randomUUID().toString();
        this.userInfo = encryptedUserInfo;
        this.mission1 = false;
        this.mission2 = false;
        this.mission3 = false;
        this.mission4 = false;
        this.mission5 = false;
        this.mission6 = false;
        this.finished = false;
    }

    public void updateMission1() { this.mission1 = true; }

    public void updateMission2() { this.mission2 = true; }

    public void updateMission3() { this.mission3 = true; }

    public void updateMission4() { this.mission4 = true; }

    public void updateMission5() { this.mission5 = true; }

    public void updateMission6() { this.mission6 = true; }

    public void setFinished() { this.finished = true; }

}
