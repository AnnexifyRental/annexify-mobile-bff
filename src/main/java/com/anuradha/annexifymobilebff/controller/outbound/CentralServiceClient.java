package com.anuradha.annexifymobilebff.controller.outbound;

import com.anuradha.annexifymobilebff.dto.IdResponseDto;
import com.anuradha.annexifymobilebff.dto.PostDto;
import com.anuradha.annexifymobilebff.dto.PostImageSaveDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CentralServiceClient {

    private final RestTemplate restTemplate;

    @Value("${annexify.service.central.url}")
    private String baseUrl;

    public CentralServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IdResponseDto savePost(PostDto postDto) {
        return restTemplate.postForObject(baseUrl + "/post", postDto, IdResponseDto.class);
    }

    public List<PostDto> findAllPosts() {
        return List.of(Objects.requireNonNull(restTemplate.getForObject(baseUrl + "/post", PostDto[].class)));
    }

    public void deletePost(String id) {
        restTemplate.delete(baseUrl + "/post?id=" + id);
    }

    public void uploadPostImages(String id, String thumbnailUrl, List<String> imageUrls) {
        restTemplate.put(baseUrl + "/post/images", new PostImageSaveDto(id, thumbnailUrl, imageUrls));
    }
}
