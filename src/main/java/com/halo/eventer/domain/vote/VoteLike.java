package com.halo.eventer.domain.vote;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "vote_like", indexes = {
        @Index(name = "idx_vote_ulid", columnList = "vote_id, ulid")
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
