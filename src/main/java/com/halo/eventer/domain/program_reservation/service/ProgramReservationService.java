package com.halo.eventer.domain.program_reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.MemberRole;
import com.halo.eventer.domain.member.repository.MemberRepository;
import com.halo.eventer.domain.program_reservation.*;
import com.halo.eventer.domain.program_reservation.dto.request.ReservationConfirmRequest;
import com.halo.eventer.domain.program_reservation.dto.request.ReservationHoldRequest;
import com.halo.eventer.domain.program_reservation.dto.response.*;
import com.halo.eventer.domain.program_reservation.repository.*;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.halo.eventer.global.error.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProgramReservationService {
    private static final int DEFAULT_HOLD_MINUTES = 10;

    private final ProgramRepository programRepository;
    private final ProgramSlotRepository slotRepository;
    private final ProgramReservationRepository reservationRepository;
    private final ProgramBlockRepository programBlockRepository;
    private final FestivalCommonTemplateRepository templateRepository;
    private final ProgramTagRepository programTagRepository;
    private final MemberRepository memberRepository;
    private final MySqlDbLockRepository dbLockRepository;
    private final EntityManager entityManager;
    private final ReservationExpireService reservationExpireService;

    @Transactional(readOnly = true)
    public AvailableProgramListResponse getVisiblePrograms(Long memberId, Long festivalId) {
        getMember(memberId); // 사용자 권한 검증 (visitor/super-admin)

        List<Program> programs = programRepository.findAllVisibleForUser(festivalId, LocalDateTime.now());

        List<AvailableProgramResponse> responses = programs.stream()
                .map(program -> {
                    List<ProgramTag> tags = programTagRepository.findAllByProgramIdOrderBySortOrder(program.getId());
                    List<ProgramSlot> slots =
                            slotRepository.findAllByProgramIdOrderBySlotDateAscStartTimeAsc(program.getId());

                    Map<LocalDate, List<ProgramSlot>> slotsByDate = slots.stream()
                            .collect(Collectors.groupingBy(
                                    ProgramSlot::getSlotDate, LinkedHashMap::new, Collectors.toList()));

                    List<ReservationDateListResponse.DateItem> dateItems = slotsByDate.entrySet().stream()
                            .map(entry -> {
                                boolean isReservable = entry.getValue().stream().anyMatch(this::isSlotReservable);
                                return ReservationDateListResponse.DateItem.of(entry.getKey(), isReservable);
                            })
                            .toList();

                    return AvailableProgramResponse.from(program, tags, dateItems);
                })
                .toList();

        return AvailableProgramListResponse.of(responses);
    }

    @Transactional(readOnly = true)
    public UserProgramDetailResponse getVisibleProgram(Long memberId, Long programId) {
        getMember(memberId);
        Program program = getVisibleProgram(programId);

        List<ProgramTag> tags = programTagRepository.findAllByProgramIdOrderBySortOrder(programId);
        List<ProgramSlot> slots = slotRepository.findAllByProgramIdOrderBySlotDateAscStartTimeAsc(programId);
        List<ProgramBlock> blocks = programBlockRepository.findAllByProgramIdOrderBySortOrder(programId);
        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(
                program.getFestival().getId());

        Map<LocalDate, List<ProgramSlot>> slotsByDate = slots.stream()
                .collect(Collectors.groupingBy(ProgramSlot::getSlotDate, LinkedHashMap::new, Collectors.toList()));

        List<ReservationDateListResponse.DateItem> dateItems = slotsByDate.entrySet().stream()
                .map(entry -> {
                    boolean isReservable = entry.getValue().stream().anyMatch(this::isSlotReservable);
                    return ReservationDateListResponse.DateItem.of(entry.getKey(), isReservable);
                })
                .toList();

        return UserProgramDetailResponse.from(program, tags, dateItems, blocks, templates);
    }

    @Transactional(readOnly = true)
    public ReservationDateListResponse getReservationDates(Long memberId, Long programId) {
        getMember(memberId);
        Program program = getProgram(programId);
        List<ProgramSlot> allSlots = slotRepository.findAllByProgramIdOrderBySlotDateAscStartTimeAsc(programId);

        Map<LocalDate, List<ProgramSlot>> slotsByDate = allSlots.stream()
                .collect(Collectors.groupingBy(ProgramSlot::getSlotDate, LinkedHashMap::new, Collectors.toList()));

        List<ReservationDateListResponse.DateItem> dateItems = slotsByDate.entrySet().stream()
                .map(entry -> {
                    boolean isReservable = entry.getValue().stream().anyMatch(this::isSlotReservable);
                    return ReservationDateListResponse.DateItem.of(entry.getKey(), isReservable);
                })
                .toList();

        return ReservationDateListResponse.of(
                program.getId(), program.getMaxPersonCount(), program.getHoldMinutes(), dateItems);
    }

    @Transactional(readOnly = true)
    public ReservationSlotListResponse getReservationSlots(Long memberId, Long programId, LocalDate date) {
        getMember(memberId);
        Program program = getProgram(programId);

        List<ProgramSlot> allSlots = slotRepository.findAllByProgramIdOrderBySlotDateAscStartTimeAsc(programId);

        List<ReservationSlotListResponse.SlotItem> slotItems = allSlots.stream()
                .filter(slot -> slot.getSlotDate().equals(date))
                .map(ReservationSlotListResponse.SlotItem::from)
                .toList();

        return ReservationSlotListResponse.of(program.getId(), date, program.getMaxPersonCount(), slotItems);
    }

    @Transactional
    public ReservationHoldResponse holdReservation(
            Long memberId, String idempotencyKey, ReservationHoldRequest request) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new BaseException("Idempotency-Key 헤더가 필요합니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        String lockKey = "program:" + request.getProgramId() + ":member:" + memberId;
        if (!dbLockRepository.tryLock(lockKey, 2)) {
            throw new BaseException("동시 예약 처리 중입니다. 잠시 후 다시 시도해 주세요.", ErrorCode.TOO_MANY_REQUESTS);
        }

        try {
            // 멱등 처리
            reservationRepository
                    .findWithProgramByMemberIdAndIdempotencyKey(memberId, idempotencyKey)
                    .ifPresent(existing -> {
                        boolean sameIntent = existing.getProgram().getId().equals(request.getProgramId())
                                && existing.getSlot().getId().equals(request.getSlotId())
                                && existing.getHeadcount() == request.getHeadcount();
                        if (!sameIntent) {
                            throw new BaseException(
                                    "Idempotency-Key가 다른 예약 시도에 재사용되었습니다.", ErrorCode.IDEMPOTENCY_KEY_REUSED);
                        }
                        throw new AlreadyProcessedReservation(existing);
                    });

            Member member = getMember(memberId);
            Program program = getProgram(request.getProgramId());

            validateHeadcount(request.getHeadcount(), program.getMaxPersonCount());
            validateAccumulatedHeadcount(memberId, program, request.getHeadcount());

            ProgramSlot slot = slotRepository
                    .findByIdAndProgramIdForUpdate(request.getSlotId(), program.getId())
                    .orElseThrow(() -> new BaseException("존재하지 않는 슬롯입니다.", ErrorCode.ENTITY_NOT_FOUND));

            slot.decreaseCapacity(request.getHeadcount());

            int holdMinutes = program.getHoldMinutes() != null ? program.getHoldMinutes() : DEFAULT_HOLD_MINUTES;
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(holdMinutes);

            ProgramReservation reservation =
                    ProgramReservation.hold(program, slot, member, request.getHeadcount(), expiresAt);
            reservation.setIdempotencyKey(idempotencyKey);

            try {
                reservationRepository.save(reservation);
            } catch (DataIntegrityViolationException e) {
                entityManager.clear();
                ProgramReservation existing = reservationRepository
                        .findWithProgramByMemberIdAndIdempotencyKey(memberId, idempotencyKey)
                        .orElseThrow(() -> e);
                return buildHoldResponse(existing);
            }
            return buildHoldResponse(reservation);

        } catch (AlreadyProcessedReservation e) {
            entityManager.clear();
            return buildHoldResponse(e.getReservation());
        } finally {
            dbLockRepository.unlock(lockKey);
        }
    }

    @Transactional(readOnly = true)
    public ReservationCheckoutResponse getCheckout(Long memberId, Long reservationId) {
        // 먼저 만료 처리 커밋
        boolean expiredNow =
                reservationExpireService.expireIfNeededForCheckout(memberId, reservationId, LocalDateTime.now());

        // checkout 렌더링용 조회
        ProgramReservation r = reservationRepository
                .findByIdAndMemberId(reservationId, memberId)
                .orElseThrow(() -> new BaseException("예약을 찾을 수 없습니다.", ErrorCode.ENTITY_NOT_FOUND));

        if (expiredNow || r.isExpired())
            throw new BaseException("예약이 만료되었습니다. 다시 예약해 주세요.", ErrorCode.RESERVATION_EXPIRED);

        Program program = r.getProgram();
        List<ProgramBlock> cautionBlocks = programBlockRepository.findAllByProgramIdAndTypeOrderBySortOrder(
                program.getId(), ProgramBlock.BlockType.CAUTION);
        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(
                program.getFestival().getId());

        return ReservationCheckoutResponse.from(r, cautionBlocks, templates);
    }

    @Transactional
    public ReservationConfirmResponse confirmReservation(
            Long memberId, Long reservationId, ReservationConfirmRequest request) {
        // 0) 만료 처리 먼저 커밋(필요 시) - checkout과 동일 전략
        boolean expiredNow =
                reservationExpireService.expireIfNeededForCheckout(memberId, reservationId, LocalDateTime.now());

        // 1) 예약 + 슬롯을 비관락으로 가져오기 (동시 confirm/expire 방지)
        ProgramReservation r = reservationRepository
                .findByIdAndMemberIdForUpdate(reservationId, memberId)
                .orElseThrow(() -> new BaseException("예약을 찾을 수 없습니다.", ErrorCode.ENTITY_NOT_FOUND));

        // 2) 이미 확정이면 멱등 처리 (같은 결과 반환)
        if (r.getStatus() == ProgramReservationStatus.CONFIRMED || r.getStatus() == ProgramReservationStatus.APPROVED) {
            return new ReservationConfirmResponse(r.getId());
        }

        // 3) 만료면 실패 (expiredNow 포함)
        if (expiredNow || r.getStatus() == ProgramReservationStatus.EXPIRED) {
            throw new BaseException("예약이 만료되었습니다. 다시 예약해 주세요.", ErrorCode.RESERVATION_EXPIRED);
        }

        // 4) HOLD만 확정 가능
        if (r.getStatus() != ProgramReservationStatus.HOLD) {
            throw new BaseException("확정할 수 없는 예약 상태입니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        r.setBookerInfo(request.getBookerName(), request.getBookerPhone());
        r.setVisitorInfo(request.getVisitorName(), request.getVisitorPhone());

        // 6) 확정 처리
        r.confirm();

        // 7) todo: 알림톡은 afterCommit으로
        // registerAfterCommitSend(r.getId());

        return new ReservationConfirmResponse(r.getId());
    }

    public ReservationResponse getReservationDetail(Long memberId, Long reservationId) {
        ProgramReservation reservation = reservationRepository
                .findByIdAndMemberId(reservationId, memberId)
                .orElseThrow(() -> new BaseException("예약을 찾을 수 없습니다.", ErrorCode.ENTITY_NOT_FOUND));
        List<ProgramTag> tags = programTagRepository.findAllByProgramIdOrderBySortOrder(
                reservation.getProgram().getId());
        return ReservationResponse.from(reservation, tags);
    }

    @Transactional(readOnly = true)
    public ReservationListResponse getReservations(Long memberId) {
        List<ProgramReservation> reservations =
                reservationRepository.findAllByMemberIdAndStatusOrderByConfirmedAtDescIdDesc(
                        memberId, ProgramReservationStatus.CONFIRMED);

        List<ReservationResponse> responses = reservations.stream()
                .map(r -> {
                    List<ProgramTag> tags = programTagRepository.findAllByProgramIdOrderBySortOrder(
                            r.getProgram().getId());
                    return ReservationResponse.from(r, tags);
                })
                .toList();

        return ReservationListResponse.of(responses);
    }

    @Transactional
    public ReservationCancelResponse cancelReservation(Long memberId, Long reservationId) {
        ProgramReservation r = reservationRepository
                .findByIdAndMemberIdForUpdate(reservationId, memberId)
                .orElseThrow(() -> new BaseException("예약을 찾을 수 없습니다.", ErrorCode.ENTITY_NOT_FOUND));

        // 멱등: 이미 취소면 같은 결과 반환
        if (r.getStatus() == ProgramReservationStatus.CANCELED) {
            return new ReservationCancelResponse(r.getId());
        }

        // 취소 가능 상태 제한: CONFIRMED만
        if (r.getStatus() != ProgramReservationStatus.CONFIRMED) {
            throw new BaseException("취소할 수 없는 예약 상태입니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        // 취소 정책(전날 18:00 컷오프)
        LocalDateTime slotStartAt = resolveSlotStartAt(r.getSlot());
        LocalDateTime cutoffAt = slotStartAt.toLocalDate().minusDays(1).atTime(18, 0);
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(cutoffAt)) { // cutoffAt 이후면 취소 불가 (cutoffAt까지 허용)
            throw new BaseException("취소 가능 시간이 지났습니다.", ErrorCode.CANCEL_NOT_ALLOWED);
        }

        r.cancel();

        // slot도 FOR UPDATE로 잡고 복구 (정합성)
        ProgramSlot slot = slotRepository
                .findByIdAndProgramIdForUpdate(
                        r.getSlot().getId(), r.getProgram().getId())
                .orElseThrow(() -> new BaseException("존재하지 않는 슬롯입니다.", ErrorCode.ENTITY_NOT_FOUND));

        slot.increaseCapacity(r.getHeadcount());

        return new ReservationCancelResponse(r.getId());
    }

    private LocalDateTime resolveSlotStartAt(ProgramSlot slot) {
        if (slot.getTemplate().getSlotType() == ProgramSlotType.DATE) {
            // 날짜형: 시작 시각이 없으니 해당 날짜 00:00으로 간주
            return slot.getSlotDate().atStartOfDay();
        }
        // 시간형: slotDate + startTime
        LocalTime startTime = slot.getStartTime();
        if (startTime == null) {
            throw new BaseException("슬롯 시작 시간이 없습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return LocalDateTime.of(slot.getSlotDate(), startTime);
    }

    private void validateHeadcount(int headcount, int maxPersonCount) {
        if (maxPersonCount > 0 && headcount > maxPersonCount) {
            throw new BaseException("최대 예약 인원(" + maxPersonCount + "명)을 초과할 수 없습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private boolean isSlotReservable(ProgramSlot slot) {
        if (slot.getSlotType() == ProgramSlotType.DATE) {
            return true;
        }
        return slot.getCapacityRemaining() != null && slot.getCapacityRemaining() > 0;
    }

    private Program getProgram(Long programId) {
        return programRepository
                .findById(programId)
                .orElseThrow(() -> new BaseException("존재하지 않는 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND));
    }

    private Program getVisibleProgram(Long programId) {
        Program program = getProgram(programId);
        LocalDateTime now = LocalDateTime.now();

        if (!program.isActive()) {
            throw new BaseException("현재 노출되지 않는 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND);
        }
        if (program.getActiveStartAt() != null && program.getActiveStartAt().isAfter(now)) {
            throw new BaseException("아직 노출되지 않는 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND);
        }
        if (program.getActiveEndAt() != null && program.getActiveEndAt().isBefore(now)) {
            throw new BaseException("노출이 종료된 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND);
        }
        if (program.getBookingOpenAt() != null && program.getBookingOpenAt().isAfter(now)) {
            throw new BaseException("아직 예약이 시작되지 않은 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND);
        }
        if (program.getBookingCloseAt() != null && program.getBookingCloseAt().isBefore(now)) {
            throw new BaseException("예약이 마감된 프로그램입니다.", ErrorCode.ENTITY_NOT_FOUND);
        }

        return program;
    }

    private Member getMember(Long memberId) {
        return memberRepository
                .findById(memberId)
                .filter(m -> m.getRole() == MemberRole.VISITOR || m.getRole() == MemberRole.SUPER_ADMIN)
                .orElseThrow(() -> new BaseException("존재하지 않는 회원입니다.", MEMBER_NOT_FOUND));
    }

    @Transactional
    public ProgramSlot getForUpdate(Long slotId) {
        return slotRepository
                .findByIdForUpdate(slotId)
                .orElseThrow(() -> new BaseException("존재하지 않는 슬롯입니다.", ErrorCode.ENTITY_NOT_FOUND));
    }

    /**
     * 같은 프로그램에서 사용자가 이미 홀드/예약한 인원까지 합산해 최대 인원을 넘지 않도록 검증.
     * - 포함: HOLD(아직 만료되지 않은 것), CONFIRMED, APPROVED
     * - 제외: EXPIRED, CANCELED, REJECTED, 만료된 HOLD
     */
    private void validateAccumulatedHeadcount(Long memberId, Program program, int newHeadcount) {
        int max = program.getMaxPersonCount();
        if (max <= 0) return; // 무제한이면 스킵

        int used = reservationRepository.sumActiveHeadcountByMemberAndProgram(
                memberId, program.getId(), LocalDateTime.now());
        if (used + newHeadcount > max) {
            throw new BaseException(
                    "이미 예약(또는 홀드)한 인원 " + used + "명으로 인해 최대 예약 인원(" + max + "명)을 초과할 수 없습니다.",
                    ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private ReservationHoldResponse buildHoldResponse(ProgramReservation reservation) {
        Program program = reservation.getProgram();
        List<ProgramBlock> cautionBlocks = programBlockRepository.findAllByProgramIdAndTypeOrderBySortOrder(
                program.getId(), ProgramBlock.BlockType.CAUTION);
        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(
                program.getFestival().getId());

        int holdMinutes = program.getHoldMinutes() != null ? program.getHoldMinutes() : DEFAULT_HOLD_MINUTES;
        return ReservationHoldResponse.from(reservation, holdMinutes, cautionBlocks, templates);
    }

    @Getter
    private static class AlreadyProcessedReservation extends BaseException {
        private final ProgramReservation reservation;

        public AlreadyProcessedReservation(ProgramReservation reservation) {
            super("이미 처리된 예약입니다.", ErrorCode.CONFLICT);
            this.reservation = reservation;
        }
    }
}
