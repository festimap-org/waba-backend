package com.halo.eventer.domain.vote;

import jakarta.persistence.*;

import com.halo.eventer.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "candidate",
        uniqueConstraints =
                @UniqueConstraint(
                        name = "uq_vote_code",
                        columnNames = {"vote_id", "code"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @Column(nullable = false, length = 30)
    private String code;

    @Column(name = "display_name", nullable = false, length = 30)
    private String displayName;

    @Column(name = "real_name", length = 30)
    private String realName;

    @Column(length = 50)
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(length = 20)
    private String phone;

    @Column(name = "vote_count", nullable = false)
    private long voteCount = 0;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = true;

    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    public static Candidate create(
            Vote vote,
            String code,
            String displayName,
            String realName,
            String description,
            String imageUrl,
            String phone,
            int displayOrder) {
        Candidate candidate = new Candidate();
        candidate.vote = vote;
        candidate.code = code;
        candidate.displayName = displayName;
        candidate.realName = realName;
        candidate.description = description;
        candidate.imageUrl = imageUrl;
        candidate.phone = phone;
        candidate.displayOrder = displayOrder;
        return candidate;
    }

    public void update(String displayName, String realName, String description, String imageUrl, String phone) {
        this.displayName = displayName;
        this.realName = realName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.phone = phone;
    }

    public void updateEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void incrementVoteCount() {
        this.voteCount++;
    }

    public void decrementVoteCount() {
        this.voteCount = Math.max(0, this.voteCount - 1);
    }

    public void updateDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
