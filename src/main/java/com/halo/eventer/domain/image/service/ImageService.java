package com.halo.eventer.domain.image.service;


import com.halo.eventer.domain.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Client s3Client;
    @Value("${cloud.aws.region.s3}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;



    /** MultipartFile을 전달받아 File로 전환한 후 S3에 업로드  */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException,Exception {

        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        String url=uploads(uploadFile,dirName);



        //log.info("url:{}",imageResDto.getImageUrl());
        return url;
    }


    private String uploads(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        try {
            // 업로드 요청 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .acl("public-read") // PublicRead 권한 부여
                    .build();

            // S3로 파일 업로드
            PutObjectResponse response = s3Client.putObject(putObjectRequest, Paths.get(uploadFile.getAbsolutePath()));

            // 업로드된 파일의 URL 반환
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(
//                new PutObjectRequest(bucket, fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
//        );
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
    private Optional<File> convert(MultipartFile file) throws  IOException,Exception {
        String contentType = file.getContentType();
        String originalFileExtension;
        // 확장자명이 존재하지 않을 경우 처리 x
        if (ObjectUtils.isEmpty(contentType)) {
            throw new Exception("파일 확장자 명이 존재하지 않습니다."); //BadRequest
        }
        else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
            if (contentType.contains("image/jpeg"))
                originalFileExtension = ".jpg";
            else if (contentType.contains("image/png"))
                originalFileExtension = ".png";
            else if (contentType.contains("image/jpg"))
                originalFileExtension =".jpg";
            else if(contentType.contains("image/webp")){
                originalFileExtension =".webp";
            }
            else  // 다른 확장자일 경우 처리 x
                throw new Exception("해당 파일 확장자는 지원하지 않습니다.");
        }
        String new_file_name = System.nanoTime() + originalFileExtension;

        File convertFile = new File(new_file_name);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}

