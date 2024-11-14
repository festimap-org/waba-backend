package com.halo.eventer.domain.vote.repository;

import com.halo.eventer.domain.vote.VoteLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteLikeRepository extends JpaRepository<VoteLike, Long> {

    @Query("SELECT v FROM VoteLike v WHERE v.vote.id = :voteId and v.ulid = :ulid ")
    Optional<VoteLike> findByVote_IdAndUlid(Long voteId,String ulid);
}
