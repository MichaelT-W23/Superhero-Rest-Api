package com.superherobackend.superhero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

    @Bean
    public S3Client s3Client() {
        // You can customize this based on your region and credentials
        return S3Client.builder()
                .region(Region.US_EAST_1)  // Set your preferred AWS region here
                .build();
    }
}
