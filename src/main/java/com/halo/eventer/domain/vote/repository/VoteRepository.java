package com.halo.eventer.domain.vote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.vote.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAllByFestival_Id(Long festivalId);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.candidates WHERE v.id = :id")
    java.util.Optional<Vote> findByIdWithCandidates(@Param("id") Long id);
}
