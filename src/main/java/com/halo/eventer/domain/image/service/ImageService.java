package com.halo.eventer.domain.image.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "png", "jpeg", "webp","jgp");

    private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    /** MultipartFile을 전달받아 File로 전환한 후 S3에 업로드  */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException,Exception {

        if(!isAllowedFileExtension(multipartFile.getOriginalFilename())){
            throw new BaseException(ErrorCode.UNACCEPTABLE_EXTENSION);
        }
        return uploadFileToS3Direct(multipartFile,dirName);
    }


    private String uploadFileToS3Direct(MultipartFile uploadFile, String dirName) throws IOException{

        InputStream inputStream = uploadFile.getInputStream();

        String objectKey = dirName + "/" + UlidCreator.getUlid().toString() + "."+FilenameUtils.getExtension(uploadFile.getOriginalFilename());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(uploadFile.getContentType())
                .contentLength(uploadFile.getSize())
                .acl("public-read") // PublicRead 권한 부여
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, uploadFile.getSize()));


        return s3Client.utilities().getUrl(b-> b.bucket(bucket).key(objectKey)).toString();     // 업로드된 파일의 S3 URL 주소 반환
    }

    public String generatePresignedUploadUrl(String fileExtension) {
        String ulid = UlidCreator.getUlid().toString();

        if(!isAllowedFileExtension(fileExtension)){
            throw new BaseException(ErrorCode.UNACCEPTABLE_EXTENSION);
        }
        // 파일 확장자 추출
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key("uploads/" + ulid + "." + fileExtension)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(objectRequest)
                .signatureDuration(Duration.ofMinutes(15))
                .build();

        return presigner.presignPutObject(presignRequest).url().toString();
    }

    private boolean isAllowedFileExtension(String fileName) {
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }
}

