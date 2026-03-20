package com.halo.eventer.domain.vote.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.fixture.VoteFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
class VoteTest {

    private Vote vote;
    private final LocalDateTime 현재 = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        vote = VoteFixture.투표_생성();
    }

    @Nested
    class isVotable {

        @BeforeEach
        void 투표_활성화_설정() {
            setField(vote, "isActive", true);
            setField(vote, "voteEnabled", true);
        }

        @Test
        void isActive가_false이면_false() {
            setField(vote, "isActive", false);

            assertThat(vote.isVotable()).isFalse();
        }

        @Test
        void voteEnabled가_false이면_false() {
            setField(vote, "voteEnabled", false);

            assertThat(vote.isVotable()).isFalse();
        }

        @Test
        void 투표_시작_전이면_false() {
            setField(vote, "voteStartAt", 현재.plusDays(1));

            assertThat(vote.isVotable()).isFalse();
        }

        @Test
        void 투표_종료_후이면_false() {
            setField(vote, "voteEndAt", 현재.minusDays(1));

            assertThat(vote.isVotable()).isFalse();
        }

        @Test
        void 시간_설정_없으면_true() {
            assertThat(vote.isVotable()).isTrue();
        }

        @Test
        void 투표_기간_내이면_true() {
            setField(vote, "voteStartAt", 현재.minusDays(1));
            setField(vote, "voteEndAt", 현재.plusDays(1));

            assertThat(vote.isVotable()).isTrue();
        }
    }

    @Nested
    class isDisplayable {

        @BeforeEach
        void 노출_활성화_설정() {
            setField(vote, "isActive", true);
            setField(vote, "displayEnabled", true);
        }

        @Test
        void isActive가_false이면_false() {
            setField(vote, "isActive", false);

            assertThat(vote.isDisplayable()).isFalse();
        }

        @Test
        void displayEnabled가_false이면_false() {
            setField(vote, "displayEnabled", false);

            assertThat(vote.isDisplayable()).isFalse();
        }

        @Test
        void 노출_시작_전이면_false() {
            setField(vote, "displayStartAt", 현재.plusDays(1));

            assertThat(vote.isDisplayable()).isFalse();
        }

        @Test
        void 노출_종료_후이면_false() {
            setField(vote, "displayEndAt", 현재.minusDays(1));

            assertThat(vote.isDisplayable()).isFalse();
        }

        @Test
        void 시간_설정_없으면_true() {
            assertThat(vote.isDisplayable()).isTrue();
        }

        @Test
        void 노출_기간_내이면_true() {
            setField(vote, "displayStartAt", 현재.minusDays(1));
            setField(vote, "displayEndAt", 현재.plusDays(1));

            assertThat(vote.isDisplayable()).isTrue();
        }
    }
}
