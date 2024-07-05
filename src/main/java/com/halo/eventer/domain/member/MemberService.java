package com.halo.eventer.domain.member;


import com.halo.eventer.global.exception.common.AccessDenyException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.member.dto.LoginDto;
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

    public TokenDto login(LoginDto loginDto) throws NoDataInDatabaseException,AccessDenyException {
        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new NoDataInDatabaseException("잘못된 아이디 혹은 비밀번호"));
        if(!encoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new AccessDenyException("비밀번호가 틀렸거나 아이디가 올바르지 않습니다."); // 401
        }

        return new TokenDto(jwtProvider.createToken(member.getLoginId(),member.getAuthorities()));
        //return jwtProvider.createToken(member.getLoginId(),member.getAuthorities());
    }
}
