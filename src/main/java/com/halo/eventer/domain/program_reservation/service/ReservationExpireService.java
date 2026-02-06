package com.halo.eventer.domain.program_reservation.service;

import com.halo.eventer.domain.program_reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.ProgramSlot;
import com.halo.eventer.domain.program_reservation.repository.ProgramReservationRepository;
import com.halo.eventer.domain.program_reservation.ProgramReservationStatus;
import com.halo.eventer.domain.program_reservation.repository.ProgramSlotRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationExpireService {

    private final ProgramReservationRepository reservationRepository;
    private final ProgramSlotRepository programSlotRepository;

    /**
     * 만료된 HOLD 예약을 최대 batchSize개 처리
     */
    @Transactional
    public int expireExpiredHoldsBatch(int batchSize) {
        LocalDateTime now = LocalDateTime.now();

        // 만료된 HOLD id를 최대 batchSize개 가져옴
        List<Long> ids = reservationRepository.findExpiredHoldIds(now, PageRequest.of(0, batchSize));
        int processed = 0;

        for (Long id : ids) {
            try {
                boolean ok = expireOneIfNeeded(id, now);
                if (ok) processed++;
            } catch (Exception e) {
                // 한 건 실패해도 다음 건 처리 계속
                log.warn("[ReservationExpireService] expireOne failed. reservationId={}", id, e);
            }
        }
        return processed;
    }

    /**
     * 단건 만료 처리
     * - 비관락으로 예약 row를 잡고
     * - HOLD && expiresAt < now면 EXPIRED 처리 + capacity 복구
     */
    @Transactional
    public boolean expireOneIfNeeded(Long reservationId, LocalDateTime now) {
        ProgramReservation r = reservationRepository.findByIdForUpdate(reservationId).orElse(null);
        if (r == null) return false;

        if (r.getStatus() != ProgramReservationStatus.HOLD) return false;

        LocalDateTime expiresAt = r.getHoldExpiresAt();
        if (expiresAt == null || !expiresAt.isBefore(now)) return false;

        r.expire(); // status=EXPIRED (엔티티 메서드)

        ProgramSlot slot = programSlotRepository.findByIdAndProgramIdForUpdate(r.getSlot().getId(), r.getProgram().getId()).orElseThrow(() -> new BaseException("존재하지 않는 슬롯입니다.", ErrorCode.ENTITY_NOT_FOUND));
        slot.increaseCapacity(r.getHeadcount());
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean expireIfNeededForCheckout(Long memberId, Long reservationId, LocalDateTime now) {
        ProgramReservation r = reservationRepository.findCheckoutByIdAndMemberIdForUpdate(reservationId, memberId).orElseThrow(() -> new BaseException("예약을 찾을 수 없습니다.", ErrorCode.ENTITY_NOT_FOUND));

        if (!r.isHold()) return false;

        LocalDateTime expiresAt = r.getHoldExpiresAt();
        if (expiresAt == null || !expiresAt.isBefore(now)) return false;

        r.expire();
        ProgramSlot slot = programSlotRepository.findByIdAndProgramIdForUpdate(r.getSlot().getId(), r.getProgram().getId()).orElseThrow(() -> new BaseException("존재하지 않는 슬롯입니다.", ErrorCode.ENTITY_NOT_FOUND));
        slot.increaseCapacity(r.getHeadcount());
        return true;
    }
}
