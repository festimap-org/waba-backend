package com.halo.eventer.user.repository;

import com.halo.eventer.user.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}
