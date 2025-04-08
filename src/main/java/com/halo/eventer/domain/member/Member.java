package com.halo.eventer.domain.member;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String password;

    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberFestival> memberFestivals;

    public void setMember(List<Authority> roles) {
        this.authorities = roles;
        roles.forEach(o -> o.setMember(this));
    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public void setRoles(List<Authority> roles) {
        this.authorities = roles;
        roles.forEach(o -> o.setMember(this));
    }

    public List<String> getAuthoritiesNames() {
        return authorities.stream()
                .map(Authority::getRoleName)
                .collect(Collectors.toList());
    }
}
