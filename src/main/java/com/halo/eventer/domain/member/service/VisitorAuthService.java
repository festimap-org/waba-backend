package com.halo.eventer.domain.member.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.member.Authority;
import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.MemberRole;
import com.halo.eventer.domain.member.SocialProvider;
import com.halo.eventer.domain.member.dto.SocialLoginResult;
import com.halo.eventer.domain.member.dto.TokenDto;
import com.halo.eventer.domain.member.dto.VisitorSignupRequest;
import com.halo.eventer.domain.member.exception.MemberNotActiveException;
import com.halo.eventer.domain.member.exception.PhoneAlreadyRegisteredException;
import com.halo.eventer.domain.member.repository.MemberRepository;
import com.halo.eventer.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorAuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    /**
     * 소셜 로그인 처리
     * - 기존 회원: JWT 발급
     * - 신규: 회원가입 필요 응답
     */
    public SocialLoginResult processSocialLogin(
            SocialProvider provider, String providerId, String email, String phone) {
        Optional<Member> existingMember = memberRepository.findByProviderAndProviderId(provider, providerId);

        if (existingMember.isPresent()) {
            Member member = existingMember.get();
            if (!member.isActive()) {
                throw new MemberNotActiveException();
            }
            String token = jwtProvider.createToken(member.getId().toString(), getRoleNames(member));
            return SocialLoginResult.loggedIn(token);
        }

        return SocialLoginResult.needSignup(provider, providerId, email, phone);
    }

    /**
     * 소셜 로그인 처리 (전화번호 없이)
     */
    public SocialLoginResult processSocialLogin(SocialProvider provider, String providerId, String email) {
        return processSocialLogin(provider, providerId, email, null);
    }

    /**
     * 회원가입 완료
     * - 기본 정보 + 마케팅 동의 + 설문 정보를 한번에 처리
     */
    @Transactional
    public TokenDto completeSignup(VisitorSignupRequest request) {
        if (memberRepository.existsByPhoneAndRole(request.getPhone(), MemberRole.VISITOR)) {
            throw new PhoneAlreadyRegisteredException();
        }

        Member member = Member.createVisitor(
                request.getPhone(),
                request.getName(),
                request.getProvider(),
                request.getProviderId(),
                request.isMarketingSms(),
                request.isMarketingEmail(),
                request.isMarketingPush());
        member.addAuthority(Authority.builder().roleName("ROLE_VISITOR").build());

        // 설문 정보 설정
        member.updateSurveyInfo(
                request.getResidenceType(),
                request.getResidenceRegion(),
                request.getResidenceDistrict(),
                request.getVisitType(),
                request.getGender(),
                request.getAgeGroup(),
                request.getTransportationType());

        memberRepository.save(member);

        return new TokenDto(jwtProvider.createToken(member.getId().toString(), getRoleNames(member)));
    }

    private List<String> getRoleNames(Member member) {
        List<String> roles = member.getRoleNames();
        if (roles.isEmpty()) {
            return Collections.singletonList("ROLE_VISITOR");
        }
        return roles;
    }
}
