package com.halo.eventer.domain.program_reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import jakarta.persistence.*;

import com.halo.eventer.global.common.BaseTime;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
            @Index(name = "idx_slot_program_date_time", columnList = "program_id, slot_date, start_time")
        })
public class ProgramSlot extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ProgramSlotType slotType;

    @Column(nullable = false)
    private LocalDate slotDate;

    private LocalTime startTime;

    @Column(nullable = false)
    private Integer durationMinutes;

    private Integer capacity;

    private Integer capacityRemaining;

    @Column(nullable = false)
    private Boolean showRemaining;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private ProgramScheduleTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id")
    private ProgramTimePattern pattern;

    public static ProgramSlot ofDate(
            Program program, ProgramScheduleTemplate template, LocalDate slotDate, Integer durationMinutes) {
        ProgramSlot s = new ProgramSlot();
        s.program = program;
        s.template = template;
        s.slotType = ProgramSlotType.DATE;
        s.slotDate = slotDate;
        s.durationMinutes = durationMinutes;
        s.showRemaining = false;
        return s;
    }

    public static ProgramSlot ofTime(
            Program program,
            ProgramScheduleTemplate template,
            ProgramTimePattern pattern,
            LocalDate slotDate,
            LocalTime startTime,
            Integer durationMinutes,
            Integer capacity) {
        ProgramSlot s = new ProgramSlot();
        s.program = program;
        s.template = template;
        s.pattern = pattern;
        s.slotType = ProgramSlotType.TIME;
        s.slotDate = slotDate;
        s.startTime = startTime;
        s.durationMinutes = durationMinutes;
        s.capacity = capacity;
        s.capacityRemaining = capacity;
        s.showRemaining = false;
        return s;
    }

    public boolean updateCapacity(int newCapacity) {
        if (this.capacity == null || this.capacityRemaining == null) {
            throw new BaseException("DATE 슬롯은 정원을 변경할 수 없습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        int booked = getBooked();
        if (newCapacity < booked) {
            return false;
        }

        boolean changed = !Objects.equals(this.capacity, newCapacity);
        this.capacity = newCapacity;
        this.capacityRemaining = newCapacity - booked;
        return changed;
    }

    public int getBooked() {
        if (this.capacity == null || this.capacityRemaining == null) {
            return 0;
        }
        return this.capacity - this.capacityRemaining;
    }
}
