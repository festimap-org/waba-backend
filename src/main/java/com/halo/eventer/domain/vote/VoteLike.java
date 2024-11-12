package com.halo.eventer.domain.vote;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "vote_like", indexes = {
        @Index(name = "idx_ip_vote", columnList = "ipAddress, vote_id")
})
public class VoteLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    @JoinColumn(name = "vote_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vote vote;

    private LocalDateTime voteTime;

    public VoteLike(String ipAddress, Vote vote) {
        this.ipAddress = ipAddress;
        this.vote = vote;
        this.voteTime = LocalDateTime.now();
    }
}
