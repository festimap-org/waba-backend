package com.halo.eventer.domain.vote.repository;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.vote.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findAllByVoteIdOrderByDisplayOrderAsc(Long voteId);

    void deleteAllByVoteId(Long voteId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Candidate c WHERE c.id = :id")
    Optional<Candidate> findByIdForUpdate(@Param("id") Long id);
}
