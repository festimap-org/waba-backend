package com.halo.eventer.user.repository;

import com.halo.eventer.user.Urgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UrgentRepository extends JpaRepository<Urgent,Long> {
    List<Urgent> findAllByPopup(boolean b);
}
