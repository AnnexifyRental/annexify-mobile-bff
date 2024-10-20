package com.anuradha.annexifymobilebff.controller.inbound;


import com.anuradha.annexifymobilebff.controller.outbound.StorageServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file-uploader")
public class FileUploaderController {

    private final StorageServiceClient storageServiceClient;

    public FileUploaderController(StorageServiceClient storageServiceClient) {
        this.storageServiceClient = storageServiceClient;
    }

    @PostMapping
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        return storageServiceClient.uploadImage(file);
    }

    @GetMapping
    public ResponseEntity<byte[]> getImage(@RequestParam String fileName) {
        return ResponseEntity.ok(storageServiceClient.getFile(fileName));
    }


}
