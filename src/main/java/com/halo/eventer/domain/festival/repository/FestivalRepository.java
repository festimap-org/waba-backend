package com.halo.eventer.domain.festival.repository;

import com.halo.eventer.domain.festival.Festival;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

  Optional<Festival> findByName(String name);

  Optional<Festival> findBySubAddress(String subAddress);
}
