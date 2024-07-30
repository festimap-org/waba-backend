package com.halo.eventer.domain.inquiry.repository;

import com.halo.eventer.domain.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i WHERE i.festival.id = :festivalId")
    List<Inquiry> findAllWithFestivalId(@Param("festivalId") Long festivalId);
}
