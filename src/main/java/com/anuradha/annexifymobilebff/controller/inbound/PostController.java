package com.anuradha.annexifymobilebff.controller.inbound;

import com.anuradha.annexifymobilebff.dto.IdResponseDto;
import com.anuradha.annexifymobilebff.dto.PostDto;
import com.anuradha.annexifymobilebff.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public IdResponseDto savePost(@RequestBody PostDto postDto) {
        return postService.savePost(postDto);
    }

    @PutMapping
    public void updatePost(@RequestBody PostDto postDto) {
        postService.updatePost(postDto);
    }

    @PutMapping("images")
    public void uploadPostImages(@RequestParam String id, MultipartFile thumbnail, List<MultipartFile> images) {
        postService.uploadImages(id, thumbnail, images);
    }

    @GetMapping
    public List<PostDto> findAll() {
        return postService.findAll();
    }

    @GetMapping("my")
    public List<PostDto> findMyPosts() {
        return postService.findMyPosts();
    }

    @GetMapping("by/id")
    public PostDto findById(@RequestParam String id) {
        return postService.findById(id);
    }

    @DeleteMapping
    public void delete(@RequestParam String id) {
        postService.delete(id);
    }

}
