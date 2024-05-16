package com.halo.eventer.notice.repository;

import com.halo.eventer.common.ArticleType;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAll(Pageable pageable);

    List<Notice> findAllByFestivalAndType(Festival festival, ArticleType type);

    List<Notice> findAllByFestivalAndPicked(Festival festival, Boolean b);

    List<Notice> findAllByPicked(Boolean b);

}

