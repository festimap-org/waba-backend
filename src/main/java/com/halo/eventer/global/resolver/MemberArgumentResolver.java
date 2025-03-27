package com.halo.eventer.global.resolver;

import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.security.CustomUserDetails;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(String.class)
        && parameter.hasParameterAnnotation(MemberCheck.class);
  }

  @Override
  public String resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || auth instanceof AnonymousAuthenticationToken || !(auth.getPrincipal() instanceof CustomUserDetails)) {
      throw new BaseException("권한이 없는 사용자입니다.", ErrorCode.UN_AUTHORIZED);
    }

    CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

    return principal.getUsername();
  }
}