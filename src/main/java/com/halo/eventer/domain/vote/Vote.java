package com.halo.eventer.domain.vote;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "vote")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(length = 500, name = "vote_image_url")
    private String voteImageUrl;

    @Column(length = 500, name = "candidate_default_image_url")
    private String candidateDefaultImageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "display_enabled", nullable = false)
    private boolean displayEnabled = false;

    @Column(name = "vote_enabled", nullable = false)
    private boolean voteEnabled = false;

    @Column(name = "show_rank", nullable = false)
    private boolean showRank = true;

    @Column(name = "show_vote_count", nullable = false)
    private boolean showVoteCount = true;

    @Column(name = "allow_cancel", nullable = false)
    private boolean allowCancel = true;

    @Column(name = "display_start_at")
    private LocalDateTime displayStartAt;

    @Column(name = "display_end_at")
    private LocalDateTime displayEndAt;

    @Column(name = "vote_start_at")
    private LocalDateTime voteStartAt;

    @Column(name = "vote_end_at")
    private LocalDateTime voteEndAt;

    public static Vote create(Festival festival, String title) {
        Vote vote = new Vote();
        vote.festival = festival;
        vote.title = title;
        return vote;
    }

    public void updateInfo(
            String title,
            String voteImageUrl,
            String candidateDefaultImageUrl,
            boolean showRank,
            boolean showVoteCount,
            boolean allowCancel) {
        this.title = title;
        this.voteImageUrl = voteImageUrl;
        this.candidateDefaultImageUrl = candidateDefaultImageUrl;
        this.showRank = showRank;
        this.showVoteCount = showVoteCount;
        this.allowCancel = allowCancel;
    }

    public void updateSchedule(
            LocalDateTime displayStartAt,
            LocalDateTime displayEndAt,
            boolean displayEnabled,
            LocalDateTime voteStartAt,
            LocalDateTime voteEndAt,
            boolean voteEnabled) {
        this.displayStartAt = displayStartAt;
        this.displayEndAt = displayEndAt;
        this.displayEnabled = displayEnabled;
        this.voteStartAt = voteStartAt;
        this.voteEndAt = voteEndAt;
        this.voteEnabled = voteEnabled;
    }

    public boolean isVotable() {
        if (!isActive || !voteEnabled) return false;
        LocalDateTime now = LocalDateTime.now();
        if (voteStartAt != null && now.isBefore(voteStartAt)) return false;
        return voteEndAt == null || !now.isAfter(voteEndAt);
    }

    public boolean isDisplayable() {
        if (!isActive || !displayEnabled) return false;
        LocalDateTime now = LocalDateTime.now();
        if (displayStartAt != null && now.isBefore(displayStartAt)) return false;
        return displayEndAt == null || !now.isAfter(displayEndAt);
    }
}
