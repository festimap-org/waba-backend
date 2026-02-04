package com.halo.eventer.domain.program_reservation;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import com.halo.eventer.domain.member.Member;
import com.halo.eventer.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "idx_reservation_status_created", columnList = "status, created_at DESC")})
public class ProgramReservation extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer peopleCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProgramReservationStatus status;

    private LocalDateTime holdExpiresAt;

    @Column(nullable = false, length = 50)
    private String bookerName;

    @Column(nullable = false, length = 20)
    private String bookerPhone;

    @Column(length = 50)
    private String visitorName;

    @Column(length = 20)
    private String visitorPhone;

    private LocalDateTime confirmedAt;

    private LocalDateTime cancelledAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "slot_id", nullable = false)
    private ProgramSlot slot;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static ProgramReservation hold(
            Program program,
            ProgramSlot slot,
            Member member,
            Integer peopleCount,
            String bookerName,
            String bookerPhone,
            String visitorName,
            String visitorPhone,
            LocalDateTime holdExpiresAt) {
        ProgramReservation r = new ProgramReservation();
        r.program = program;
        r.slot = slot;
        r.member = member;
        r.peopleCount = peopleCount;
        r.status = ProgramReservationStatus.HOLD;
        r.holdExpiresAt = holdExpiresAt;
        r.bookerName = bookerName;
        r.bookerPhone = bookerPhone;
        r.visitorName = visitorName;
        r.visitorPhone = visitorPhone;
        return r;
    }

    public void confirm() {
        this.status = ProgramReservationStatus.CONFIRMED;
        this.confirmedAt = LocalDateTime.now();
    }

    public void expire() {
        this.status = ProgramReservationStatus.EXPIRED;
    }

    public void cancel() {
        this.status = ProgramReservationStatus.CANCELED;
        this.cancelledAt = LocalDateTime.now();
    }

    public void changeStatus(ProgramReservationStatus status) {
        this.status = status;
    }
}
