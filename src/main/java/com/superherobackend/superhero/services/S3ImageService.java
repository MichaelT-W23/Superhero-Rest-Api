package com.superherobackend.superhero.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.superherobackend.superhero.models.Image;
import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.repositories.ImageRepository;
import com.superherobackend.superhero.repositories.SuperheroRepository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;

@Service
public class S3ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SuperheroRepository superheroRepository;

    @Autowired
    private S3Client s3Client;

    private final String BUCKET_NAME = "superhero-pics";


    public InputStream getImageBySuperhero(Long superId) {
        Image image = imageRepository.findBySuperId(superId);

        if (image == null) {
            throw new RuntimeException("Image not found for superhero ID: " + superId);
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(image.getStoredFilename())
                .build();

        return s3Client.getObject(getObjectRequest);
    }
    
    
    public void uploadImage(Long superId, MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        }

        String storedFilename = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(storedFilename)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        Superhero superhero = superheroRepository.findById(superId)
            .orElseThrow(() -> new RuntimeException("Superhero not found with ID: " + superId));

        Image image = new Image();
        image.setOriginalFilename(originalFilename);
        image.setStoredFilename(storedFilename);
        image.setSuperhero(superhero);

        imageRepository.save(image);
    }
    
}

