package com.halo.eventer.domain.concert_info.repository;

import com.halo.eventer.domain.concert_info.ConcertInfo;
import com.halo.eventer.domain.concert_info.ConcertInfoType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, Long> {
  List<ConcertInfo> findAllByFestivalIdAndType(Long festivalId, ConcertInfoType type);
}
