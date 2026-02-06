package com.halo.eventer.domain.program_reservation.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationExpireScheduler {
    private static final int BATCH_SIZE = 50;

    private final ReservationExpireService reservationExpireService;

    /**
     * 만료된 HOLD 예약을 정리(EXPIRED)하고 슬롯 capacity를 복구
     */
    @Scheduled(cron = "0 */30 * * * *", zone = "Asia/Seoul")
    public void expireHolds() {
        try {
            int processed = reservationExpireService.expireExpiredHoldsBatch(BATCH_SIZE);
            if (processed > 0) {
                log.info("[ReservationExpireScheduler] expired holds processed={}", processed);
            }
        } catch (Exception e) {
            // 스케줄러 전체가 죽지 않도록 방어
            log.error("[ReservationExpireScheduler] expireHolds failed", e);
        }
    }
}
