package com.halo.eventer.domain.vote;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "vote_like", indexes = {
        @Index(name = "idx_ulid", columnList = "ulid")
})
public class VoteLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ulid;

    @JoinColumn(name = "vote_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vote vote;

    @Setter
    private LocalDateTime voteTime;

    public VoteLike(String ulid, Vote vote) {
        this.ulid = ulid;
        this.vote = vote;
        this.voteTime = LocalDateTime.now();
    }


}
