package com.halo.eventer.domain.member.repository;

import com.halo.eventer.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByLoginId(String loginId);
}
