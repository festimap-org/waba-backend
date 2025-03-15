package com.halo.eventer.domain.stamp.repository;

import com.halo.eventer.domain.stamp.StampUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampUserRepository extends JpaRepository<StampUser, Long> {
    Optional<StampUser> findByUuid(String uuid);

    boolean existsByStampIdAndPhone(Long stampId, String phone);

    Optional<StampUser> findByStampIdAndPhoneAndName(Long stampId, String phone, String name);
}
