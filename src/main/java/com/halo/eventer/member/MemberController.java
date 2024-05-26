package com.halo.eventer.member;

import com.halo.eventer.member.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto) {
//        String token = memberService.login(loginDto);
//
//        ResponseCookie cookie = ResponseCookie.from("JWT", token)
//                .httpOnly(true)
//                .secure(false)
//                .path("/")
//                .maxAge(24*60*60)
//                .sameSite("Lax")
//                .domain("wabauniv.com")
//                .build();
//
//        response.setHeader("Set-Cookie", cookie.toString());

        return memberService.login(loginDto);
    }
}
