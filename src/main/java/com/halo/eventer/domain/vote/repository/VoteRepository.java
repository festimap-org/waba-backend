package com.halo.eventer.domain.vote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.vote.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAllByFestival_Id(Long festivalId);
}
