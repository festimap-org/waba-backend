package com.halo.eventer.domain.image.controller;


import com.halo.eventer.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "이미지 업로드", description = "각 부분에 대한 이미지 업로드")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> uploadImage(
            @RequestParam(value = "image",required = true) MultipartFile files,
            @RequestParam(value = "dirName") String name){
        try {
            String image = imageService.upload(files, name);
            return ResponseEntity.status(HttpStatus.OK).body(image);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/upload-url")
    public String getUploadUrl(@RequestParam String fileExtension) {
        return imageService.generatePresignedUploadUrl(fileExtension);
    }

}
