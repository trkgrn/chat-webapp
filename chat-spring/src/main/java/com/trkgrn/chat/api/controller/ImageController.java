package com.trkgrn.chat.api.controller;

import com.trkgrn.chat.api.model.concretes.Image;
import com.trkgrn.chat.api.service.concretes.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(this.imageService.uploadImage(imageFile));
    }

    @GetMapping(path = { "/get/{imageId}" })
    public Image getImage(@PathVariable("imageId") Long imageId) {
        return this.imageService.getImage(imageId);
    }


}
