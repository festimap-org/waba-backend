package com.halo.eventer.domain.vote.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.vote.Vote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAllByFestival(Festival festival);
}

