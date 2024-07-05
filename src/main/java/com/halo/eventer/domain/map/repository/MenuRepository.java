package com.halo.eventer.domain.map.repository;

import com.halo.eventer.domain.map.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByIdIn(List<Long> ids);
    List<Menu> findAllByMap_Id(Long id);
}
