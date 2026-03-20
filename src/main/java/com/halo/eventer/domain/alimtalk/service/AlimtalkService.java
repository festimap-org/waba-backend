package com.halo.eventer.domain.alimtalk.service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.alimtalk.AlimtalkOutbox;
import com.halo.eventer.domain.alimtalk.AlimtalkOutboxStatus;
import com.halo.eventer.domain.alimtalk.button.AlimtalkButton;
import com.halo.eventer.domain.alimtalk.repository.AlimtalkRepository;
import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.MemberRole;
import com.halo.eventer.domain.member.repository.MemberRepository;
import com.halo.eventer.domain.program_reservation.*;
import com.halo.eventer.domain.program_reservation.entity.content.FestivalCommonTemplate;
import com.halo.eventer.domain.program_reservation.entity.content.ProgramBlock;
import com.halo.eventer.domain.program_reservation.entity.reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.entity.slot.ProgramSlot;
import com.halo.eventer.domain.program_reservation.entity.slot.ProgramSlotType;
import com.halo.eventer.domain.program_reservation.repository.FestivalCommonTemplateRepository;
import com.halo.eventer.domain.program_reservation.repository.ProgramBlockRepository;
import com.halo.eventer.domain.program_reservation.repository.ProgramReservationRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.halo.eventer.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.halo.eventer.global.error.ErrorCode.RESERVATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlimtalkService {
    private static final String PROGRAM_DOMAIN_SUFFIX = ".program.festiv.kr";

    private static final DateTimeFormatter KR_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd(E)", Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);

    private final MemberRepository memberRepository;
    private final ProgramReservationRepository reservationRepository;
    private final ProgramBlockRepository programBlockRepository;
    private final FestivalCommonTemplateRepository templateRepository;
    private final AlimtalkRepository alimtalkRepository;
    private final AlimtalkSendWorker sendWorker;
    private final ObjectMapper objectMapper;

    @Value("${ncp.sens.template.programReservationConfirmed:}")
    private String tplProgramReservationConfirm;

    @Value("${ncp.sens.template.programReservationCanceled:}")
    private String tplProgramReservationCancel;

    @Value("${ncp.sens.template.programReminderD1:}")
    private String tplProgramReminderD1;

    @Value("${ncp.sens.template.programReminderD0:}")
    private String tplProgramReminderD0;

    @Transactional
    public void enqueueReservationConfirm(Long memberId, Long reservationId) {
        Member member = getMember(memberId);
        ProgramReservation reservation = getReservation(reservationId);

        Program program = reservation.getProgram();
        ProgramSlot slot = reservation.getSlot();

        String dateStr = slot.getSlotDate().format(KR_DATE_FORMAT);
        String timeStr = formatTimeStr(slot);
        String duration = program.getDurationTime();
        int headCount = reservation.getPeopleCount();
        int price = program.getPriceAmount() * headCount;

        // 1) CAUTION 조회 (ProgramBlock)
        List<ProgramBlock> cautionBlocks = programBlockRepository.findAllByProgramIdAndTypeOrderBySortOrder(
                program.getId(), ProgramBlock.BlockType.CAUTION);

        List<String> requiredLines = cautionBlocks.stream()
                .map(b -> {
                    if (b.getCautionContent() != null && !b.getCautionContent().isBlank()) return b.getCautionContent();
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        String requiredNotice = toBullets(requiredLines);

        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(
                program.getFestival().getId());
        String commonNotice = formatFestivalCommonTemplates(templates);

        String content = "[프로그램 예약 안내]\n" + "\n"
                + reservation.getBookerName()
                + "님, 프로그램 예약 완료되었습니다 😊\n" + "\n"
                + "< 프로그램 정보 >\n"
                + program.getFestival().getName()
                + "\n" + program.getName()
                + "\n" + "\n"
                + "📅 신청 회차: "
                + dateStr + timeStr + "\n" + "⏱ 소요 시간: 총 "
                + duration + "\n" + "👥 참여 인원: "
                + headCount + "명\n" + "💰 결제 금액: "
                + price + "원\n" + "\n"
                + "📌 필수 확인 사항\n"
                + requiredNotice
                + "\n" + "\n"
                + "🚨 공통 안내 사항\n"
                + commonNotice;

        String subDomain = program.getFestival().getSubDomain();
        String reservationConfirmUrl = buildProgramUrl(subDomain, "/myreservation");
        String programDetailUrl = buildProgramUrl(subDomain, "/detail?id=" + program.getId());
        String programListUrl = buildProgramUrl(subDomain, "");

        List<AlimtalkButton> buttons = List.of(
                AlimtalkButton.wl("내 예약 확인", reservationConfirmUrl),
                AlimtalkButton.wl("예약 프로그램 상세 보기", programDetailUrl),
                AlimtalkButton.wl("다른 프로그램 보기", programListUrl));

        enqueue(tplProgramReservationConfirm, requirePhone(member), content, buttons);
    }

    @Transactional
    public void enqueueReservationCanceled(Long memberId, Long reservationId) {
        Member member = getMember(memberId);
        ProgramReservation reservation = getReservation(reservationId);

        Program program = reservation.getProgram();
        ProgramSlot slot = reservation.getSlot();

        String dateStr = slot.getSlotDate().format(KR_DATE_FORMAT);
        String timeStr = formatTimeStr(slot);
        int headCount = reservation.getPeopleCount();
        int price = program.getPriceAmount() * headCount;

        String content = "[프로그램 예약 취소 안내]\n" + "\n"
                + reservation.getBookerName()
                + "님, 프로그램 예약이 취소되었습니다 😊\n" + "\n"
                + "< 취소 프로그램 정보 >\n"
                + program.getFestival().getName()
                + "\n" + program.getName()
                + "\n" + "\n"
                + "📅 취소 회차: "
                + dateStr + timeStr + "\n" + "💰 환불 예정 금액: "
                + price + "원\n" + "\n"
                + "※ 결제 방법에 따라 환불은 영업일 기준 최대 7일 이상 소요될 수 있습니다.";

        String subDomain = program.getFestival().getSubDomain();
        String reservationConfirmUrl = buildProgramUrl(subDomain, "/myreservation");
        String programListUrl = buildProgramUrl(subDomain, "");

        List<AlimtalkButton> buttons = List.of(
                AlimtalkButton.wl("내 예약 확인", reservationConfirmUrl), AlimtalkButton.wl("다른 프로그램 보기", programListUrl));

        enqueue(tplProgramReservationCancel, requirePhone(member), content, buttons);
    }

    @Transactional
    public void enqueueProgramReminderD1(Long memberId, Long reservationId) {
        Member member = getMember(memberId);
        ProgramReservation reservation = getReservation(reservationId);

        Program program = reservation.getProgram();
        ProgramSlot slot = reservation.getSlot();

        String dateStr = slot.getSlotDate().format(KR_DATE_FORMAT);
        String timeStr = formatTimeStr(slot);
        int headCount = reservation.getPeopleCount();
        String meetingPlace = "";

        // 1) CAUTION 조회 (ProgramBlock)
        List<ProgramBlock> cautionBlocks = programBlockRepository.findAllByProgramIdAndTypeOrderBySortOrder(
                program.getId(), ProgramBlock.BlockType.CAUTION);

        List<String> requiredLines = cautionBlocks.stream()
                .map(b -> {
                    if (b.getCautionContent() != null && !b.getCautionContent().isBlank()) return b.getCautionContent();
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        String requiredNotice = toBullets(requiredLines);

        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(
                program.getFestival().getId());
        String commonNotice = formatFestivalCommonTemplates(templates);

        String content = reservation.getBookerName() + "님, 신청하신 프로그램이 내일 진행됩니다 😊\n" + "\n"
                + "< 프로그램 정보 >\n"
                + program.getFestival().getName()
                + "\n" + program.getName()
                + "\n" + "\n"
                + "📅 진행 일시: "
                + dateStr + timeStr + "\n" + "👥 참여 인원: "
                + headCount + "명\n" + "📍 집결 장소: "
                + meetingPlace + "\n" + "\n"
                + "📌 안내 사항\n"
                + requiredNotice
                + "\n" + "\n"
                + "※ 집결 장소 및 상세 안내는 ‘프로그램 상세’에서 확인하실 수 있습니다.";

        String subDomain = program.getFestival().getSubDomain();
        String reservationConfirmUrl = buildProgramUrl(subDomain, "/myreservation");
        String programDetailUrl = buildProgramUrl(subDomain, "/detail?id=" + program.getId());
        String programListUrl = buildProgramUrl(subDomain, "");

        List<AlimtalkButton> buttons = List.of(
                AlimtalkButton.wl("내 예약 확인", reservationConfirmUrl),
                AlimtalkButton.wl("예약 프로그램 상세 보기", programDetailUrl),
                AlimtalkButton.wl("다른 프로그램 보기", programListUrl));

        enqueue(tplProgramReservationConfirm, requirePhone(member), content, buttons);
    }

    @Transactional
    public void enqueueProgramReminderD0(Long memberId, Long reservationId) {
        Member member = getMember(memberId);
        ProgramReservation reservation = getReservation(reservationId);

        Program program = reservation.getProgram();
        ProgramSlot slot = reservation.getSlot();

        String timeStr = formatTimeStr(slot);
        int headCount = reservation.getPeopleCount();
        String meetingPlace = "";

        // 1) CAUTION 조회 (ProgramBlock)
        List<ProgramBlock> cautionBlocks = programBlockRepository.findAllByProgramIdAndTypeOrderBySortOrder(
                program.getId(), ProgramBlock.BlockType.CAUTION);

        List<String> requiredLines = cautionBlocks.stream()
                .map(b -> {
                    if (b.getCautionContent() != null && !b.getCautionContent().isBlank()) return b.getCautionContent();
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        String requiredNotice = toBullets(requiredLines);

        List<FestivalCommonTemplate> templates = templateRepository.findAllByFestivalIdOrderBySortOrder(
                program.getFestival().getId());
        String commonNotice = formatFestivalCommonTemplates(templates);

        String content = "[프로그램 시작 예정 안내]\n" + "\n"
                + reservation.getBookerName()
                + "님, 신청하신 프로그램이 곧 시작됩니다 😊\n" + "\n"
                + "< 프로그램 정보 >\n"
                + program.getFestival().getName()
                + "\n" + program.getName()
                + "\n" + "\n"
                + "📅 시작 시간: "
                + timeStr + "\n" + "👥 참여 인원: "
                + headCount + "명\n" + "📍 집결 장소: "
                + meetingPlace + "\n" + "\n"
                + "📌 안내 사항\n"
                + requiredNotice
                + "\n" + "\n"
                + "※ 집결 장소 및 상세 안내는 ‘프로그램 상세’에서 확인하실 수 있습니다.";

        String subDomain = program.getFestival().getSubDomain();
        String reservationConfirmUrl = buildProgramUrl(subDomain, "/myreservation");
        String programDetailUrl = buildProgramUrl(subDomain, "/detail?id=" + program.getId());
        String programListUrl = buildProgramUrl(subDomain, "");

        List<AlimtalkButton> buttons = List.of(
                AlimtalkButton.wl("내 예약 확인", reservationConfirmUrl),
                AlimtalkButton.wl("예약 프로그램 상세 보기", programDetailUrl),
                AlimtalkButton.wl("다른 프로그램 보기", programListUrl));

        enqueue(tplProgramReservationConfirm, requirePhone(member), content, buttons);
    }

    private void enqueue(String templateCode, String toPhone, String content, List<AlimtalkButton> buttons) {
        String buttonsJson;
        try {
            buttonsJson = objectMapper.writeValueAsString(buttons);
        } catch (JsonProcessingException e) {
            throw new BaseException("알림톡 버튼 JSON 직렬화 실패", ErrorCode.INTERNAL_SERVER_ERROR);
        }

        AlimtalkOutbox outbox =
                alimtalkRepository.save(AlimtalkOutbox.pending(templateCode, toPhone, content, buttonsJson));
        log.info(
                "[ALIMTALK][ENQUEUE] outboxId={}, templateCode={}, to={}, contentLen={}, buttonsJson={}",
                outbox.getId(),
                templateCode,
                toPhone,
                content == null ? 0 : content.length(),
                buttonsJson);

        registerAfterCommitSend(outbox.getId());
    }

    private void registerAfterCommitSend(Long outboxId) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            throw new BaseException("트랜잭션 동기화가 활성화되지 않았습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                sendWorker.sendAsync(outboxId);
            }
        });
    }

    private Member getMember(Long memberId) {
        return memberRepository
                .findById(memberId)
                .filter(m -> m.getRole() == MemberRole.VISITOR || m.getRole() == MemberRole.SUPER_ADMIN)
                .orElseThrow(() -> new BaseException("존재하지 않는 회원입니다.", MEMBER_NOT_FOUND));
    }

    private ProgramReservation getReservation(Long reservationId) {
        return reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new BaseException("존재하지 않는 예약입니다.", RESERVATION_NOT_FOUND));
    }

    private String requirePhone(Member member) {
        String phone = member.getPhone();
        if (phone == null || phone.isBlank()) {
            throw new BaseException("회원 휴대폰 번호가 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND);
        }
        return phone;
    }

    private String toBullets(List<String> rawLines) {
        // rawLines: 항목 단위(또는 긴 문장) 리스트
        List<String> lines = new ArrayList<>();
        for (String raw : rawLines) {
            if (raw == null) continue;
            // 이미 여러 줄이면 줄 단위로 분해
            for (String s : raw.split("\\r?\\n")) {
                String t = s.trim();
                if (t.isEmpty()) continue;
                lines.add("• " + t);
            }
        }
        return lines.isEmpty() ? "• (안내 없음)" : String.join("\n", lines);
    }

    private String formatFestivalCommonTemplates(List<FestivalCommonTemplate> templates) {
        if (templates == null || templates.isEmpty()) {
            return "• (안내 없음)";
        }

        List<String> blocks = new ArrayList<>();
        for (FestivalCommonTemplate t : templates) {
            String title = safeTrim(t.getTitle()); // 필드명 맞춰서 변경
            String content = safeTrim(t.getContent()); // 필드명 맞춰서 변경

            if (title == null && content == null) continue;

            StringBuilder sb = new StringBuilder();

            // • 제목
            if (title != null) {
                sb.append("• ").append(title);
            } else {
                sb.append("• 안내");
            }

            // 줄바꿈 + 내용
            if (content != null) {
                sb.append("\n").append(content);
            }

            blocks.add(sb.toString());
        }

        return blocks.isEmpty() ? "• (안내 없음)" : String.join("\n\n", blocks); // 블록 사이 한 줄 띄움
    }

    private String safeTrim(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private String formatTimeStr(ProgramSlot slot) {
        if (slot.getTemplate().getSlotType() == ProgramSlotType.DATE) return "";
        return slot.getStartTime() != null ? " " + slot.getStartTime().toString() : "";
    }

    private String buildProgramUrl(String subAddress, String path) {
        return "https://" + subAddress + PROGRAM_DOMAIN_SUFFIX + path;
    }

    @Transactional(readOnly = true)
    public Page<AlimtalkOutbox> getOutboxList(AlimtalkOutboxStatus status, Pageable pageable) {
        if (status == null) {
            return alimtalkRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        return alimtalkRepository.findByStatusOrderByCreatedAtDesc(status, pageable);
    }

    @Transactional
    public void resend(Long outboxId) {
        AlimtalkOutbox outbox = alimtalkRepository
                .findById(outboxId)
                .orElseThrow(() -> new BaseException("존재하지 않는 알림톡 메시지입니다.", ErrorCode.ENTITY_NOT_FOUND));

        outbox.markPendingForResend();
        log.info(
                "[ALIMTALK][RESEND] outboxId={}, templateCode={}, to={}",
                outboxId,
                outbox.getTemplateCode(),
                outbox.getToPhone());

        registerAfterCommitSend(outbox.getId());
    }

    @Transactional
    public void resendAll(List<Long> outboxIds) {
        for (Long outboxId : outboxIds) {
            resend(outboxId);
        }
    }
}
