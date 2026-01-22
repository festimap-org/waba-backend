package com.halo.eventer.domain.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.halo.eventer.domain.member.SocialProvider;
import com.halo.eventer.domain.member.dto.OAuthUserProfile;
import com.halo.eventer.domain.member.exception.OAuthException;
import com.halo.eventer.global.config.OAuthProperties;
import com.halo.eventer.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoOAuthService {

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;
    private final OAuthProperties oAuthProperties;

    public KakaoOAuthService(
            @Qualifier("simpleRestTemplate") RestTemplate restTemplate, OAuthProperties oAuthProperties) {
        this.restTemplate = restTemplate;
        this.oAuthProperties = oAuthProperties;
    }

    public String getAuthorizationUrl() {
        OAuthProperties.Provider kakao = oAuthProperties.getKakao();
        return String.format(
                "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                kakao.getClientId(), kakao.getRedirectUri());
    }

    public OAuthUserProfile getUserProfile(String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        return getUserInfo(accessToken);
    }

    private String getAccessToken(String authorizationCode) {
        OAuthProperties.Provider kakao = oAuthProperties.getKakao();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakao.getClientId());
        params.add("client_secret", kakao.getClientSecret());
        params.add("redirect_uri", kakao.getRedirectUri());
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

            if (response.getBody() == null || !response.getBody().containsKey("access_token")) {
                log.error("Kakao token response missing access_token: {}", response.getBody());
                throw new OAuthException(ErrorCode.OAUTH_TOKEN_REQUEST_FAILED);
            }

            return (String) response.getBody().get("access_token");
        } catch (RestClientException e) {
            log.error("Kakao token request failed", e);
            throw new OAuthException(ErrorCode.OAUTH_TOKEN_REQUEST_FAILED, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private OAuthUserProfile getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, request, Map.class);

            if (response.getBody() == null) {
                log.error("Kakao user info response is null");
                throw new OAuthException(ErrorCode.OAUTH_USER_INFO_REQUEST_FAILED);
            }

            Map<String, Object> body = response.getBody();
            String providerId = String.valueOf(body.get("id"));

            Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
            String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
            String phone = null;
            if (kakaoAccount != null && kakaoAccount.get("phone_number") != null) {
                phone = normalizePhoneNumber((String) kakaoAccount.get("phone_number"));
            }

            return new OAuthUserProfile(SocialProvider.KAKAO, providerId, email, phone);
        } catch (RestClientException e) {
            log.error("Kakao user info request failed", e);
            throw new OAuthException(ErrorCode.OAUTH_USER_INFO_REQUEST_FAILED, e.getMessage());
        }
    }

    private String normalizePhoneNumber(String phone) {
        if (phone == null) {
            return null;
        }
        // Kakao returns phone like "+82 10-1234-5678", normalize to "010-1234-5678"
        return phone.replaceAll("\\+82 ", "0").replaceAll(" ", "");
    }
}
