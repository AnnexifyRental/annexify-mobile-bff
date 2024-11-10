package com.anuradha.annexifymobilebff.controller.outbound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StorageServiceClient {

    private final RestTemplate restTemplate;

    @Value("${annexify.service.storage.url}")
    private String baseUrl;

    public StorageServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String uploadImage(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new org.springframework.core.io.ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    baseUrl + "/file-uploader",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
        }
    }

    public byte[] getFile(String fileName) {
        return restTemplate.getForObject(baseUrl + "/file-uploader?fileName=" + fileName, byte[].class);
    }

}
