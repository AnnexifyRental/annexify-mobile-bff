package com.anuradha.annexifymobilebff.service;

import com.anuradha.annexifymobilebff.controller.outbound.CentralServiceClient;
import com.anuradha.annexifymobilebff.controller.outbound.StorageServiceClient;
import com.anuradha.annexifymobilebff.dto.IdResponseDto;
import com.anuradha.annexifymobilebff.dto.PostDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    private final CentralServiceClient centralServiceClient;
    private final StorageServiceClient storageServiceClient;

    public PostService(CentralServiceClient centralServiceClient, StorageServiceClient storageServiceClient) {
        this.centralServiceClient = centralServiceClient;
        this.storageServiceClient = storageServiceClient;
    }

    public IdResponseDto savePost(PostDto postDto) {
        return centralServiceClient.savePost(postDto);
    }

    public List<PostDto> findAll() {
        return centralServiceClient.findAllPosts();
    }


    public void uploadImages(String id, MultipartFile thumbnail, List<MultipartFile> images) {
        validateUploadImagesRequest(thumbnail, images);

        String thumbnailUrl = uploadImage(thumbnail);
        List<String> imageUrls = images.stream()
                .map(this::uploadImage)
                .filter(Objects::nonNull)
                .toList();

        centralServiceClient.uploadPostImages(id, thumbnailUrl, imageUrls);

    }

    private String uploadImage(MultipartFile image) {
        return storageServiceClient.uploadImage(image);

    }

    private void validateUploadImagesRequest(MultipartFile thumbnail, List<MultipartFile> images) {
        if (thumbnail == null || thumbnail.isEmpty() || thumbnail.getOriginalFilename() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thumbnail is required");

        if (images != null && !images.isEmpty()) {
            if (images.size() > 5)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximum 5 images are allowed");
            for (MultipartFile image : images) {
                if (image == null || image.isEmpty() || image.getOriginalFilename() == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");


                String contentType = image.getContentType();
                if (!Arrays.asList("image/jpeg", "image/png", "image/jpg").contains(contentType)) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid file type. Only PNG, JPEG, and JPG images are allowed.");
                }
            }
        }

    }

    public void delete(String id) {
        centralServiceClient.deletePost(id);
    }


}
