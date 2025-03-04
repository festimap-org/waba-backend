package com.halo.eventer.domain.missing_person.repository;


import com.halo.eventer.domain.missing_person.MissingPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;

public interface MissingPersonRepository extends JpaRepository<MissingPerson, Long> {
    List<MissingPerson> findAllByFestival_idAndPopup(Long festival_id, Boolean popup);
}
