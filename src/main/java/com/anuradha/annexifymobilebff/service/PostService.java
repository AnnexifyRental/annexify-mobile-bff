package com.anuradha.annexifymobilebff.service;

import com.anuradha.annexifymobilebff.controller.outbound.CentralServiceClient;
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

    public PostService(CentralServiceClient centralServiceClient) {
        this.centralServiceClient = centralServiceClient;
    }

    public IdResponseDto savePost(PostDto postDto) {
        return centralServiceClient.savePost(postDto);
    }

    public List<PostDto> findAll() {
        return centralServiceClient.findAllPosts();
    }


    public void uploadImages(String id, MultipartFile thumbnail, List<MultipartFile> images) {
        validateUploadImagesRequest(thumbnail, images);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));

        post.setThumbnail(uploadImage(thumbnail));
        postRepository.save(post);

        if (images == null || images.isEmpty()) return;
        postImageRepository.saveAll(
                images.stream()
                        .map(x -> new PostImage(post, uploadImage(x)))
                        .filter(x -> Objects.nonNull(x.getUrl()))
                        .toList()
        );

    }

    private String uploadImage(MultipartFile image) {
//            return awsS3Client.uploadFile("central-images/" + image.getOriginalFilename(), image.getBytes(), image.getContentType());
        return fileUploaderService.uploadFile(image);
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
