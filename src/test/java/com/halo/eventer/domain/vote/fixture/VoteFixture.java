package com.halo.eventer.domain.vote.fixture;

import java.time.LocalDateTime;
import java.util.List;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.FestivalFixture;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.dto.request.VoteCreateRequest;
import com.halo.eventer.domain.vote.dto.request.VoteInfoUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.VoteScheduleUpdateRequest;

import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
public class VoteFixture {

    public static final Long 투표_ID = 1L;
    public static final Long 존재하지_않는_투표_ID = 999L;
    public static final Long 축제_ID = 1L;

    public static Vote 투표_생성() {
        Festival festival = FestivalFixture.축제_엔티티();
        Vote vote = Vote.create(festival, "테스트 투표");
        setField(vote, "id", 투표_ID);
        return vote;
    }

    public static List<Vote> 투표_목록_생성() {
        return List.of(투표_생성(), 투표_생성());
    }

    public static VoteCreateRequest 투표_생성_요청() {
        VoteCreateRequest request = new VoteCreateRequest();
        setField(request, "title", "테스트 투표");
        return request;
    }

    public static VoteInfoUpdateRequest 투표_정보_수정_요청() {
        VoteInfoUpdateRequest request = new VoteInfoUpdateRequest();
        setField(request, "title", "수정된 제목");
        setField(request, "voteImageUrl", "https://image.url/vote.jpg");
        setField(request, "candidateDefaultImageUrl", "https://image.url/candidate.jpg");
        setField(request, "showRank", true);
        setField(request, "showVoteCount", false);
        setField(request, "allowCancel", true);
        return request;
    }

    public static VoteScheduleUpdateRequest 투표_일정_수정_요청() {
        VoteScheduleUpdateRequest request = new VoteScheduleUpdateRequest();
        setField(request, "displayStartAt", LocalDateTime.of(2026, 5, 1, 10, 0));
        setField(request, "displayEndAt", LocalDateTime.of(2026, 5, 10, 22, 0));
        setField(request, "displayEnabled", true);
        setField(request, "voteStartAt", LocalDateTime.of(2026, 5, 2, 10, 0));
        setField(request, "voteEndAt", LocalDateTime.of(2026, 5, 9, 22, 0));
        setField(request, "voteEnabled", true);
        return request;
    }

    public static VoteScheduleUpdateRequest 노출_시작이_종료_이후인_일정_수정_요청() {
        VoteScheduleUpdateRequest request = new VoteScheduleUpdateRequest();
        setField(request, "displayStartAt", LocalDateTime.of(2026, 5, 10, 22, 0));
        setField(request, "displayEndAt", LocalDateTime.of(2026, 5, 1, 10, 0));
        setField(request, "displayEnabled", true);
        setField(request, "voteStartAt", LocalDateTime.of(2026, 5, 2, 10, 0));
        setField(request, "voteEndAt", LocalDateTime.of(2026, 5, 9, 22, 0));
        setField(request, "voteEnabled", true);
        return request;
    }

    public static VoteScheduleUpdateRequest 투표_시작이_종료_이후인_일정_수정_요청() {
        VoteScheduleUpdateRequest request = new VoteScheduleUpdateRequest();
        setField(request, "displayStartAt", LocalDateTime.of(2026, 5, 1, 10, 0));
        setField(request, "displayEndAt", LocalDateTime.of(2026, 5, 10, 22, 0));
        setField(request, "displayEnabled", true);
        setField(request, "voteStartAt", LocalDateTime.of(2026, 5, 9, 22, 0));
        setField(request, "voteEndAt", LocalDateTime.of(2026, 5, 2, 10, 0));
        setField(request, "voteEnabled", true);
        return request;
    }
}
