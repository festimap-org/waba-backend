package com.halo.eventer.domain.member.oauth;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.member.SocialProvider;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialOAuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final Map<SocialProvider, String> USER_INFO_URLS = Map.of(
            SocialProvider.KAKAO, "https://kapi.kakao.com/v2/user/me",
            SocialProvider.NAVER, "https://openapi.naver.com/v1/nid/me");

    public SocialUserInfo getUserInfo(SocialProvider provider, String accessToken) {
        String url = USER_INFO_URLS.get(provider);
        if (url == null) {
            throw new BaseException(ErrorCode.INVALID_SOCIAL_PROVIDER);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            return parseUserInfo(provider, response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Social token verification failed: provider={}, status={}", provider, e.getStatusCode());
            throw new BaseException(ErrorCode.INVALID_SOCIAL_TOKEN);
        } catch (Exception e) {
            log.error("Social login error: provider={}, error={}", provider, e.getMessage());
            throw new BaseException(ErrorCode.SOCIAL_LOGIN_FAILED);
        }
    }

    private SocialUserInfo parseUserInfo(SocialProvider provider, String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            return switch (provider) {
                case KAKAO -> parseKakaoResponse(root);
                case NAVER -> parseNaverResponse(root);
            };
        } catch (Exception e) {
            log.error("Failed to parse social response: {}", e.getMessage());
            throw new BaseException(ErrorCode.SOCIAL_LOGIN_FAILED);
        }
    }

    private SocialUserInfo parseKakaoResponse(JsonNode root) {
        String id = root.get("id").asText();
        return SocialUserInfo.builder()
                .provider(SocialProvider.KAKAO)
                .providerId(id)
                .build();
    }

    private SocialUserInfo parseNaverResponse(JsonNode root) {
        JsonNode response = root.get("response");
        String id = response.get("id").asText();
        return SocialUserInfo.builder()
                .provider(SocialProvider.NAVER)
                .providerId(id)
                .build();
    }
}
