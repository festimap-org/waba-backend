package com.halo.eventer.domain.inquiry.repository;

import com.halo.eventer.domain.inquiry.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i WHERE i.festival.id = :festivalId")
    List<Inquiry> findAllWithFestivalId(@Param("festivalId") Long festivalId);

    Page<Inquiry> findAllByFestivalId(Long festivalId, Pageable pageable);

    // 1 회차용
    @Query("SELECT i FROM Inquiry i " +
            "WHERE i.festival.id = :festivalId " +
            "ORDER BY i.id DESC " +
            "LIMIT :size")
    List<Inquiry> getFirstPage(@Param("festivalId") Long festivalId,
                             @Param("size") int size);

    // n 회차용
    @Query("SELECT i FROM Inquiry i " +
            "WHERE i.festival.id = :festivalId AND i.id < :lastId  " +
            "ORDER BY i.id DESC " +
            "LIMIT :size")
    List<Inquiry> getNextPageByEventIdAndLastId(@Param("festivalId") Long festivalId,
                                              @Param("lastId") Long lastId,
                                              @Param("size") int size);
}
