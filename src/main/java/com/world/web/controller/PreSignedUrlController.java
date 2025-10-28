package com.world.web.controller;

import com.world.web.serviceImpl.S3PresignedUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/s3")
public class PreSignedUrlController {

    private final S3PresignedUrlService s3PresignedUrlService;

    public PreSignedUrlController(S3PresignedUrlService s3PresignedUrlService) {
        this.s3PresignedUrlService = s3PresignedUrlService;
    }

    /**
     *
     * @param key as Query Parameter ?key=test-file.txt
     * @param minutes as time duration as &minutes=10
     * @return
     * GET <a href="http://localhost:8080/api/s3/presigned-url?key=test-file.txt&minutes=10">
     */
    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String key, @RequestParam(defaultValue = "15") int minutes) {
        String url = s3PresignedUrlService.generatePresignedUrl(key, Duration.ofMinutes(minutes));
        return ResponseEntity.ok(url);
    }
}
