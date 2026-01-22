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
public class NaverOAuthService {

    private static final String TOKEN_URL = "https://nid.naver.com/oauth2.0/token";
    private static final String USER_INFO_URL = "https://openapi.naver.com/v1/nid/me";

    private final RestTemplate restTemplate;
    private final OAuthProperties oAuthProperties;

    public NaverOAuthService(
            @Qualifier("simpleRestTemplate") RestTemplate restTemplate, OAuthProperties oAuthProperties) {
        this.restTemplate = restTemplate;
        this.oAuthProperties = oAuthProperties;
    }

    public String getAuthorizationUrl(String state) {
        OAuthProperties.Provider naver = oAuthProperties.getNaver();
        return String.format(
                "https://nid.naver.com/oauth2.0/authorize?client_id=%s&redirect_uri=%s&response_type=code&state=%s",
                naver.getClientId(), naver.getRedirectUri(), state);
    }

    public OAuthUserProfile getUserProfile(String authorizationCode, String state) {
        String accessToken = getAccessToken(authorizationCode, state);
        return getUserInfo(accessToken);
    }

    private String getAccessToken(String authorizationCode, String state) {
        OAuthProperties.Provider naver = oAuthProperties.getNaver();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naver.getClientId());
        params.add("client_secret", naver.getClientSecret());
        params.add("code", authorizationCode);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

            if (response.getBody() == null || !response.getBody().containsKey("access_token")) {
                log.error("Naver token response missing access_token: {}", response.getBody());
                throw new OAuthException(ErrorCode.OAUTH_TOKEN_REQUEST_FAILED);
            }

            return (String) response.getBody().get("access_token");
        } catch (RestClientException e) {
            log.error("Naver token request failed", e);
            throw new OAuthException(ErrorCode.OAUTH_TOKEN_REQUEST_FAILED, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private OAuthUserProfile getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, request, Map.class);

            if (response.getBody() == null) {
                log.error("Naver user info response is null");
                throw new OAuthException(ErrorCode.OAUTH_USER_INFO_REQUEST_FAILED);
            }

            Map<String, Object> body = response.getBody();
            Map<String, Object> responseData = (Map<String, Object>) body.get("response");

            if (responseData == null) {
                log.error("Naver user info response.response is null");
                throw new OAuthException(ErrorCode.OAUTH_USER_INFO_REQUEST_FAILED);
            }

            String providerId = (String) responseData.get("id");
            String email = (String) responseData.get("email");
            String phone = normalizePhoneNumber((String) responseData.get("mobile"));

            return new OAuthUserProfile(SocialProvider.NAVER, providerId, email, phone);
        } catch (RestClientException e) {
            log.error("Naver user info request failed", e);
            throw new OAuthException(ErrorCode.OAUTH_USER_INFO_REQUEST_FAILED, e.getMessage());
        }
    }

    private String normalizePhoneNumber(String phone) {
        if (phone == null) {
            return null;
        }
        // Naver returns phone like "010-1234-5678", keep as is
        return phone;
    }
}
