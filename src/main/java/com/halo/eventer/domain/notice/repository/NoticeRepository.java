package com.halo.eventer.domain.content.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.content.Content;
import com.halo.eventer.global.common.ArticleType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Content, Long> {

  Page<Content> findAll(Pageable pageable);

  List<Content> findAllByFestivalAndType(Festival festival, ArticleType type);

  List<Content> findAllByFestivalAndPicked(Festival festival, Boolean b);

  @Query("SELECT n FROM Content n WHERE n.picked = :picked AND n.festival.id = :id ")
  List<Content> findAllByPickedAndFestival_Id(Boolean picked, Long id);
}
