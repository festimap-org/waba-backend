package com.halo.eventer.domain.vote.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.halo.eventer.domain.vote.Candidate;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.fixture.CandidateFixture;
import com.halo.eventer.domain.vote.fixture.VoteFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
class CandidateTest {

    private Vote vote;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        vote = VoteFixture.투표_생성();
        candidate = CandidateFixture.후보_생성(vote, CandidateFixture.후보_ID_A, 0);
    }

    @Nested
    class incrementVoteCount {

        @Test
        void 득표수_증가() {
            candidate.incrementVoteCount();

            assertThat(candidate.getVoteCount()).isEqualTo(1);
        }

        @Test
        void 연속_증가() {
            candidate.incrementVoteCount();
            candidate.incrementVoteCount();

            assertThat(candidate.getVoteCount()).isEqualTo(2);
        }
    }

    @Nested
    class decrementVoteCount {

        @Test
        void 득표수_감소() {
            setField(candidate, "voteCount", 3L);

            candidate.decrementVoteCount();

            assertThat(candidate.getVoteCount()).isEqualTo(2);
        }

        @Test
        void 득표수가_0이면_0_유지() {
            candidate.decrementVoteCount();

            assertThat(candidate.getVoteCount()).isEqualTo(0);
        }
    }

    @Nested
    class update {

        @Test
        void 후보_정보_수정() {
            candidate.update("수정된 이름", "홍길동", "설명", "https://image.url/new.jpg", "010-1234-5678");

            assertThat(candidate.getDisplayName()).isEqualTo("수정된 이름");
            assertThat(candidate.getRealName()).isEqualTo("홍길동");
            assertThat(candidate.getDescription()).isEqualTo("설명");
            assertThat(candidate.getImageUrl()).isEqualTo("https://image.url/new.jpg");
            assertThat(candidate.getPhone()).isEqualTo("010-1234-5678");
        }

        @Test
        void null로_수정가능() {
            candidate.update(null, null, null, null, null);

            assertThat(candidate.getDisplayName()).isNull();
            assertThat(candidate.getImageUrl()).isNull();
        }
    }

    @Nested
    class updateEnabled {

        @Test
        void 비활성화() {
            candidate.updateEnabled(false);

            assertThat(candidate.isEnabled()).isFalse();
        }

        @Test
        void 재활성화() {
            candidate.updateEnabled(false);
            candidate.updateEnabled(true);

            assertThat(candidate.isEnabled()).isTrue();
        }
    }

    @Nested
    class updateDisplayOrder {

        @Test
        void 노출_순서_변경() {
            candidate.updateDisplayOrder(5);

            assertThat(candidate.getDisplayOrder()).isEqualTo(5);
        }
    }
}
