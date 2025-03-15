package com.halo.eventer.infra.naver.sms.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.infra.naver.sms.dto.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SmsRequest {

    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    public String makeSignature(Long time, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }



    public HttpHeaders getSmsHeader(String url) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Long time = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time,url));

        return headers;
    }

    public SmsReqDto getSmsBodyWithFile(List<MessageDto> messages,List<FileDto> files, String content){

        //file이 null이면 LMS로 감
        SmsReqDto request = SmsReqDto.builder()
                .type("MMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content(content)
                .messages(messages)
                .files(files)
                .build();

        return request;
    }

    public SmsResDto sendSmsReq(Object smsReqDto) throws JsonProcessingException, URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        // 요청 만들기
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(smsReqDto);
        HttpEntity<String> httpBody = new HttpEntity<>(body, getSmsHeader("/sms/v2/services/"+ this.serviceId+ "/messages"));

        // 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResDto response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResDto.class);

        return response;
    }
    public FileUploadResDto sendFileUpload(String fileName, String file) throws JsonProcessingException, URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        FileUploadReqDto fileUploadReqDto = new FileUploadReqDto(fileName,file);
        // 요청 만들기
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(fileUploadReqDto);
        HttpEntity<String> httpBody = new HttpEntity<>(body, getSmsHeader("/sms/v2/services/"+ this.serviceId+ "/files"));

        // 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        FileUploadResDto response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/files"), httpBody, FileUploadResDto.class);
        return response;
    }
}
//POST https://sens.apigw.ntruss.com/sms/v2/services/{serviceId}/messages
//POST https://sens.apigw.ntruss.com/sms/v2/services/{serviceId}/files
