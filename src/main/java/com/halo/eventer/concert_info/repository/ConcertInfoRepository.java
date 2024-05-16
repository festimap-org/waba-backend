package com.halo.eventer.concert_info.repository;

import com.halo.eventer.concert_info.ConcertInfo;
import com.halo.eventer.concert_info.ConcertInfoType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, Long> {
    List<ConcertInfo> findAllByFestivalIdAndType(Long festivalId, ConcertInfoType type);
}
