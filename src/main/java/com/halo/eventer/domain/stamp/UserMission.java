package com.halo.eventer.domain.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(
    name = "user_mission",
    indexes = {@Index(name = "idx_stamp_user_id", columnList = "stamp_user_id")})
public class UserMission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private boolean isComplete;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stamp_user_id")
  private StampUser stampUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mission_id")
  private Mission mission;

  public void setComplete(boolean state) {
    this.isComplete = state;
  }

  public void setStampUser(StampUser stampUser) {
    this.stampUser = stampUser;
  }

  public void setMission(Mission mission) {
    this.mission = mission;
  }
}
