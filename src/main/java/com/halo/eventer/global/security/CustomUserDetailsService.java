package com.halo.eventer.global.security;

import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws NoDataInDatabaseException {
        return new CustomUserDetails(memberRepository.findByLoginId(loginId).orElseThrow(()-> new NoDataInDatabaseException("해당 유저 정보다 존재하지 않습니다.")));
    }
}
