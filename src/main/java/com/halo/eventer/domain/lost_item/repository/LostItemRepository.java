package com.halo.eventer.domain.lost_item.repository;

import com.halo.eventer.domain.lost_item.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {}
