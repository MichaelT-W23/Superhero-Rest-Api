package com.superherobackend.superhero.controllers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.superherobackend.superhero.services.S3ImageService;


@RestController
@RequestMapping("/images")
public class S3ImageController {

    @Autowired
    private S3ImageService s3ImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("superId") Long superId,
                                              @RequestParam("file") MultipartFile file) {
        try {
            s3ImageService.uploadImage(superId, file);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/{superId}")
    public ResponseEntity<byte[]> getImageBySuperhero(@PathVariable Long superId) {
        try {
            InputStream imageStream = s3ImageService.getImageBySuperhero(superId);
            byte[] imageBytes = imageStream.readAllBytes();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // You can adjust this based on image type

            return ResponseEntity.ok().headers(headers).body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
