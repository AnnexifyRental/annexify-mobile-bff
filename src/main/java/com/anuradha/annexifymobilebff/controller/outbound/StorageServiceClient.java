package com.anuradha.annexifymobilebff.controller.outbound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceClient {

    private final RestTemplate restTemplate;

    @Value("${annexify.service.storage.url}")
    private String baseUrl;

    public StorageServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String uploadImage(MultipartFile image) {
        return restTemplate.postForObject(baseUrl + "/file-uploader", image, String.class);
    }

    public byte[] getFile(String fileName) {
        return restTemplate.getForObject(baseUrl + "/file-uploader?fileName=" + fileName, byte[].class);
    }

}
