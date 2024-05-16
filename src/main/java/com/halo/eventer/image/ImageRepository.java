package com.halo.eventer.image;

import com.halo.eventer.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteByIdIn(List<Long> ids);
}
