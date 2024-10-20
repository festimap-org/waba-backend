package com.halo.eventer.domain.notice.repository;

import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.global.common.ArticleType;
import com.halo.eventer.domain.festival.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAll(Pageable pageable);

    List<Notice> findAllByFestivalAndType(Festival festival, ArticleType type);

    List<Notice> findAllByFestivalAndPicked(Festival festival, Boolean b);

    @Query("SELECT n FROM Notice n WHERE n.picked = :picked AND n.festival.id = :id ")
    List<Notice> findAllByPickedAndFestival_Id(Boolean picked, Long id);

}

