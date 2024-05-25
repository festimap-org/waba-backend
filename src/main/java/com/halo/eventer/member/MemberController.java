package com.halo.eventer.member;

import com.halo.eventer.member.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        String token = memberService.login(loginDto);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // 개발 환경에서는 false, 배포 환경에서는 true로 설정
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        cookie.setDomain("localhost"); // 하위 도메인에서 쿠키 공유
        response.addCookie(cookie);

        return "로그인 성공";
    }
}
