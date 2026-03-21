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
import com.halo.eventer.domain.vote.Candidate;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.dto.response.CandidateListResponse;
import com.halo.eventer.domain.vote.dto.response.CandidateResponse;
import com.halo.eventer.domain.vote.exception.CandidateNotFoundException;
import com.halo.eventer.domain.vote.exception.VoteNotFoundException;
import com.halo.eventer.domain.vote.fixture.CandidateFixture;
import com.halo.eventer.domain.vote.fixture.VoteFixture;
import com.halo.eventer.domain.vote.repository.CandidateRepository;
import com.halo.eventer.domain.vote.repository.VoteRedisRepository;
import com.halo.eventer.domain.vote.repository.VoteRepository;
import com.halo.eventer.global.error.exception.BaseException;

import static com.halo.eventer.domain.vote.fixture.CandidateFixture.*;
import static com.halo.eventer.domain.vote.fixture.VoteFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class CandidateAdminServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteRedisRepository voteRedisRepository;

    @InjectMocks
    private CandidateAdminService candidateAdminService;

    private Vote vote;
    private Candidate candidateA;
    private Candidate candidateB;

    @BeforeEach
    void setUp() {
        Festival festival = FestivalFixture.축제_엔티티();
        vote = VoteFixture.투표_생성();
        candidateA = CandidateFixture.후보_생성(vote, 후보_ID_A, 0);
        candidateB = CandidateFixture.후보_생성(vote, 후보_ID_B, 1);
    }

    @Test
    void 후보_목록_조회_이미지_없으면_기본_이미지_반환() {
        Vote voteWithDefault = VoteFixture.기본_이미지가_있는_투표_생성();
        Candidate noImageCandidate = CandidateFixture.후보_생성(voteWithDefault, 후보_ID_A, 0);
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(voteWithDefault));
        given(candidateRepository.findAllByVoteIdOrderByDisplayOrderAsc(투표_ID)).willReturn(List.of(noImageCandidate));

        List<CandidateListResponse> result = candidateAdminService.getCandidates(투표_ID);

        assertThat(result.get(0).getImageUrl()).isEqualTo("https://image.url/default.jpg");
    }

    @Test
    void 후보_목록_조회_이미지_있으면_본인_이미지_반환() {
        Vote voteWithDefault = VoteFixture.기본_이미지가_있는_투표_생성();
        Candidate withImageCandidate = CandidateFixture.이미지가_있는_후보_생성(voteWithDefault, 후보_ID_A, 0);
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(voteWithDefault));
        given(candidateRepository.findAllByVoteIdOrderByDisplayOrderAsc(투표_ID)).willReturn(List.of(withImageCandidate));

        List<CandidateListResponse> result = candidateAdminService.getCandidates(투표_ID);

        assertThat(result.get(0).getImageUrl()).isEqualTo("https://image.url/candidate.jpg");
    }

    @Test
    void 후보_추가_성공() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(vote));
        given(candidateRepository.findMaxDisplayOrderByVoteId(투표_ID)).willReturn(1);
        given(candidateRepository.save(any(Candidate.class))).willReturn(candidateA);

        CandidateResponse result = candidateAdminService.addCandidate(투표_ID, 후보_생성_요청());

        assertThat(result).isNotNull();
        assertThat(result.getDisplayOrder()).isEqualTo(2);
        then(candidateRepository).should().save(any(Candidate.class));
        then(voteRedisRepository).should().initRanking(eq(투표_ID), any(), eq(0L));
    }

    @Test
    void 후보_추가_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> candidateAdminService.addCandidate(존재하지_않는_투표_ID, 후보_생성_요청()))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 후보_수정_성공() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findById(후보_ID_A)).willReturn(Optional.of(candidateA));

        CandidateResponse result = candidateAdminService.updateCandidate(투표_ID, 후보_ID_A, 후보_수정_요청());

        assertThat(result.getDisplayName()).isEqualTo("수정된 후보");
    }

    @Test
    void 후보_수정_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> candidateAdminService.updateCandidate(존재하지_않는_투표_ID, 후보_ID_A, 후보_수정_요청()))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 후보_수정_존재하지_않는_후보_실패() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> candidateAdminService.updateCandidate(투표_ID, 존재하지_않는_후보_ID, 후보_수정_요청()))
                .isInstanceOf(CandidateNotFoundException.class);
    }

    @Test
    void 후보_활성화_비활성화_성공() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findAllByIdInAndVoteId(anyList(), eq(투표_ID))).willReturn(List.of(candidateA));

        candidateAdminService.updateCandidatesEnabled(투표_ID, 후보_활성화_요청(false, 후보_ID_A));

        assertThat(candidateA.isEnabled()).isFalse();
    }

    @Test
    void 후보_활성화_비활성화_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(
                        () -> candidateAdminService.updateCandidatesEnabled(존재하지_않는_투표_ID, 후보_활성화_요청(false, 후보_ID_A)))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 후보_활성화_비활성화_유효하지_않는_후보_실패() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findAllByIdInAndVoteId(anyList(), eq(투표_ID))).willReturn(List.of());

        assertThatThrownBy(() -> candidateAdminService.updateCandidatesEnabled(투표_ID, 후보_활성화_요청(false, 존재하지_않는_후보_ID)))
                .isInstanceOf(BaseException.class);
    }

    @Test
    void 후보_선택_삭제_성공() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findAllByIdInAndVoteId(anyList(), eq(투표_ID)))
                .willReturn(List.of(candidateA, candidateB));

        candidateAdminService.deleteCandidates(투표_ID, 후보_선택_삭제_요청(후보_ID_A, 후보_ID_B));

        then(candidateRepository).should().deleteAll(List.of(candidateA, candidateB));
    }

    @Test
    void 후보_선택_삭제_존재하지_않는_투표_실패() {
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> candidateAdminService.deleteCandidates(존재하지_않는_투표_ID, 후보_선택_삭제_요청(후보_ID_A)))
                .isInstanceOf(VoteNotFoundException.class);
    }

    @Test
    void 후보_선택_삭제_유효하지_않는_후보_실패() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findAllByIdInAndVoteId(anyList(), eq(투표_ID))).willReturn(List.of());

        assertThatThrownBy(() -> candidateAdminService.deleteCandidates(투표_ID, 후보_선택_삭제_요청(존재하지_않는_후보_ID)))
                .isInstanceOf(BaseException.class);
    }

    @Test
    void 노출_순서_변경_성공() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findAllById(anyIterable())).willReturn(List.of(candidateA, candidateB));

        candidateAdminService.updateDisplayOrder(투표_ID, 순서_변경_요청());

        assertThat(candidateA.getDisplayOrder()).isEqualTo(1);
        assertThat(candidateB.getDisplayOrder()).isEqualTo(0);
    }

    @Test
    void 노출_순서_변경_존재하지_않는_후보_실패() {
        given(voteRepository.findById(투표_ID)).willReturn(Optional.of(vote));
        given(candidateRepository.findAllById(anyIterable())).willReturn(List.of(candidateA));

        assertThatThrownBy(() -> candidateAdminService.updateDisplayOrder(투표_ID, 순서_변경_요청()))
                .isInstanceOf(BaseException.class);
    }
}
