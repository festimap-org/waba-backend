package com.halo.eventer.domain.missing_person.repository;

import com.halo.eventer.domain.missing_person.MissingPerson;
import java.util.List;
import javax.swing.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissingPersonRepository extends JpaRepository<MissingPerson, Long> {
  List<MissingPerson> findAllByFestival_idAndPopup(Long festival_id, Boolean popup);
}
