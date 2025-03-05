package com.halo.eventer.domain.member.service;


import com.halo.eventer.domain.member.Exception.LoginFailedException;
import com.halo.eventer.domain.member.Exception.MemberNotFoundException;
import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.dto.LoginDto;
import com.halo.eventer.domain.member.dto.TokenDto;
import com.halo.eventer.domain.member.repository.MemberRepository;
import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public TokenDto login(LoginDto loginDto) throws BaseException {
    Member member = memberRepository.findByLoginId(loginDto.getLoginId())
            .orElseThrow(MemberNotFoundException::new);
        if(!encoder.matches(loginDto.getPassword(), member.getPassword())) {
      throw new LoginFailedException();
        }

        return new TokenDto(jwtProvider.createToken(member.getLoginId(),member.getAuthorities()));
        //return jwtProvider.createToken(member.getLoginId(),member.getAuthorities());
    }
}
