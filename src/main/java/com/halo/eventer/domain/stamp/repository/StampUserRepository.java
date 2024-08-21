package com.halo.eventer.domain.stamp.repository;

import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampUserRepository extends JpaRepository<StampUser, Long> {
    Optional<StampUser> findByUuid(String uuid);

    boolean existsByStampIdAndUserInfo(Long stampId, String userInfo);

    Optional<StampUser> findByStampIdAndUserInfo(Long stampId, String userInfo);

    List<StampUser> findByStamp(Stamp stamp);
}
