package com.halo.eventer.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.dto.AgencySignupRequest;
import com.halo.eventer.domain.member.dto.LoginDto;
import com.halo.eventer.domain.member.dto.TokenDto;
import com.halo.eventer.domain.member.exception.CompanyEmailAlreadyExistsException;
import com.halo.eventer.domain.member.exception.LoginFailedException;
import com.halo.eventer.domain.member.exception.LoginIdAlreadyExistsException;
import com.halo.eventer.domain.member.exception.MemberNotFoundException;
import com.halo.eventer.domain.member.repository.MemberRepository;
import com.halo.eventer.domain.sms.service.SmsVerificationService;
import com.halo.eventer.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final SmsVerificationService smsVerificationService;

    public TokenDto login(LoginDto loginDto) {
        Member member = memberRepository.findByLoginId(loginDto.getLoginId()).orElseThrow(MemberNotFoundException::new);
        if (!encoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new LoginFailedException();
        }

        return new TokenDto(jwtProvider.createToken(member.getLoginId(), member.getRoleNames()));
    }

    @Transactional
    public Member signupAgency(AgencySignupRequest request) {
        smsVerificationService.validateVerified(request.getManagerPhone());
        validateSignupRequest(request);

        String encodedPassword = encoder.encode(request.getPassword());

        Member member = Member.createAgency(
                request.getLoginId(),
                encodedPassword,
                request.getCompanyEmail(),
                request.getCompanyName(),
                request.getManagerName(),
                request.getManagerPosition(),
                request.getManagerPhone());

        memberRepository.save(member);
        smsVerificationService.consumeVerification(request.getManagerPhone());

        return member;
    }

    private void validateSignupRequest(AgencySignupRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new LoginIdAlreadyExistsException();
        }

        if (memberRepository.existsByCompanyEmail(request.getCompanyEmail())) {
            throw new CompanyEmailAlreadyExistsException();
        }
    }
}
