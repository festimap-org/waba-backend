package com.halo.eventer.domain.member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.*;

import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String loginId;

    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status = MemberStatus.ACTIVE;

    @Column(name = "marketing_sms")
    private Boolean marketingSms = false;

    @Column(name = "marketing_email")
    private Boolean marketingEmail = false;

    @Column(name = "marketing_push")
    private Boolean marketingPush = false;

    // 설문 정보 (회원가입 시 선택)
    @Enumerated(EnumType.STRING)
    @Column(name = "residence_type", length = 20)
    private ResidenceType residenceType;

    @Column(name = "residence_region", length = 50)
    private String residenceRegion;

    @Column(name = "residence_district", length = 50)
    private String residenceDistrict;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type", length = 20)
    private VisitType visitType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", length = 20)
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "transportation_type", length = 20)
    private TransportationType transportationType;

    // 소셜 로그인 정보
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SocialProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StampUser> stampUsers = new ArrayList<>();

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
        this.role = MemberRole.SUPER_ADMIN;
        this.status = MemberStatus.ACTIVE;
    }

    public static Member createVisitor(String phone, String name, SocialProvider provider, String providerId) {
        Member member = new Member();
        member.phone = phone;
        member.name = name;
        member.provider = provider;
        member.providerId = providerId;
        member.role = MemberRole.VISITOR;
        member.status = MemberStatus.ACTIVE;
        return member;
    }

    public static Member createVisitor(
            String phone,
            String name,
            SocialProvider provider,
            String providerId,
            boolean marketingSms,
            boolean marketingEmail,
            boolean marketingPush) {
        Member member = createVisitor(phone, name, provider, providerId);
        member.marketingSms = marketingSms;
        member.marketingEmail = marketingEmail;
        member.marketingPush = marketingPush;
        return member;
    }

    public static Member createAgency(String loginId, String password, String phone, String name) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.phone = phone;
        member.name = name;
        member.role = MemberRole.AGENCY;
        member.status = MemberStatus.ACTIVE;
        return member;
    }

    public void setRoles(List<Authority> roles) {
        this.authorities = roles;
        roles.forEach(o -> o.setMember(this));
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.setMember(this);
    }

    public List<String> getRoleNames() {
        return authorities.stream().map(Authority::getRoleName).collect(Collectors.toList());
    }

    public boolean isVisitor() {
        return this.role == MemberRole.VISITOR;
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }

    public void updateStatus(MemberStatus status) {
        this.status = status;
    }

    public void updateMarketingConsent(boolean marketingSms, boolean marketingEmail, boolean marketingPush) {
        this.marketingSms = marketingSms;
        this.marketingEmail = marketingEmail;
        this.marketingPush = marketingPush;
    }

    public void updateSurveyInfo(
            ResidenceType residenceType,
            String residenceRegion,
            String residenceDistrict,
            VisitType visitType,
            Gender gender,
            AgeGroup ageGroup,
            TransportationType transportationType) {
        this.residenceType = residenceType;
        this.residenceRegion = residenceRegion;
        this.residenceDistrict = residenceDistrict;
        this.visitType = visitType;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.transportationType = transportationType;
    }

    public void addStampUser(StampUser stampUser) {
        this.stampUsers.add(stampUser);
    }
}
