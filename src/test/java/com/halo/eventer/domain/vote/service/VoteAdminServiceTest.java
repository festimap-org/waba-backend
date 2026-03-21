package com.halo.eventer.domain.vote.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.FestivalFixture;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.dto.response.VoteInfoResponse;
import com.halo.eventer.domain.vote.dto.response.VoteResponse;
import com.halo.eventer.domain.vote.dto.response.VoteScheduleResponse;
import com.halo.eventer.domain.vote.exception.VoteNotFoundException;
import com.halo.eventer.domain.vote.fixture.VoteFixture;
import com.halo.eventer.domain.vote.repository.CandidateRepository;
import com.halo.eventer.domain.vote.repository.VoteRedisRepository;
import com.halo.eventer.domain.vote.repository.VoteRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;

import static com.halo.eventer.domain.vote.fixture.VoteFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class VoteAdminServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private FestivalRepository festivalRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private VoteRedisRepository voteRedisRepository;

    @InjectMocks
    private VoteAdminService voteAdminService;

    private Festival festival;
    private Vote vote;

    @BeforeEach
    void setUp() {
        festival = FestivalFixture.축제_엔티티();
        vote = VoteFixture.투표_생성();
    }

    @Test
    void 투표_생성_성공() {
        given(festivalRepository.findById(anyLong())).willReturn(Optional.of(festival));

        voteAdminService.createVote(축제_ID, 투표_생성_요청());

        then(voteRepository).should().save(any(Vote.class));
    }

    @Test
    void 투표_생성_존재하지_않는_축제_실패() {
        given(festivalRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> voteAdminService.createVote(축제_ID, 투표_생성_요청()))
                .isInstanceOf(FestivalNotFoundException.class);
    }

    @Test
    void 축제별_투표_목록_조회_성공() {
        List<Vote> votes = 투표_목록_생성();
        given(voteRepository.findAllByFestival_Id(anyLong())).willReturn(votes);

        List<VoteResponse> result = voteAdminService.getVotesByFestival(축제_ID);

        assertThat(result).hasSize(votes.size());
    }

    @Test
    void 투표_기본_정보_조회_성공() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(vote));

        VoteInfoResponse result = voteAdminService.getVoteInfo(투표_ID);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(vote.getTitle());
    }

    @Test
    void 투표_기본_정보_조회_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> voteAdminService.getVoteInfo(존재하지_않는_투표_ID)).isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 투표_일정_조회_성공() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(vote));

        VoteScheduleResponse result = voteAdminService.getVoteSchedule(투표_ID);

        assertThat(result).isNotNull();
    }

    @Test
    void 투표_일정_조회_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> voteAdminService.getVoteSchedule(존재하지_않는_투표_ID))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 투표_기본_정보_수정_성공() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(vote));

        voteAdminService.updateVoteInfo(투표_ID, 투표_정보_수정_요청());

        assertThat(vote.getTitle()).isEqualTo("수정된 제목");
        assertThat(vote.getVoteImageUrl()).isEqualTo("https://image.url/vote.jpg");
        assertThat(vote.isShowVoteCount()).isFalse();
    }

    @Test
    void 투표_기본_정보_수정_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> voteAdminService.updateVoteInfo(존재하지_않는_투표_ID, 투표_정보_수정_요청()))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 투표_일정_수정_성공() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(vote));

        voteAdminService.updateVoteSchedule(투표_ID, 투표_일정_수정_요청());

        assertThat(vote.getDisplayStartAt()).isNotNull();
        assertThat(vote.getVoteStartAt()).isNotNull();
        assertThat(vote.isDisplayEnabled()).isTrue();
        assertThat(vote.isVoteEnabled()).isTrue();
    }

    @Test
    void 투표_일정_수정_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> voteAdminService.updateVoteSchedule(존재하지_않는_투표_ID, 투표_일정_수정_요청()))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 투표_일정_수정_노출_시작이_종료_이후_실패() {
        assertThatThrownBy(() -> voteAdminService.updateVoteSchedule(투표_ID, 노출_시작이_종료_이후인_일정_수정_요청()))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_SCHEDULE);
    }

    @Test
    void 투표_일정_수정_투표_시작이_종료_이후_실패() {
        assertThatThrownBy(() -> voteAdminService.updateVoteSchedule(투표_ID, 투표_시작이_종료_이후인_일정_수정_요청()))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_SCHEDULE);
    }

    @Test
    void 투표_삭제_성공() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(vote));

        voteAdminService.deleteVote(투표_ID);

        then(candidateRepository).should().deleteAllByVoteId(투표_ID);
        then(voteRepository).should().delete(vote);
        then(voteRedisRepository).should().deleteVoteCache(투표_ID);
    }

    @Test
    void 투표_삭제_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> voteAdminService.deleteVote(존재하지_않는_투표_ID)).isInstanceOf(VoteNotFoundException.class);
    }
}
