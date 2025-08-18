package com.halo.eventer.domain.stamp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.stamp.PageTemplate;
import com.halo.eventer.domain.stamp.dto.stamp.enums.PageType;

public interface PageTemplateRepository extends JpaRepository<PageTemplate, Long> {
    Optional<PageTemplate> findFirstByStampIdAndType(Long stampId, PageType type);
}
