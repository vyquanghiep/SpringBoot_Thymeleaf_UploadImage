package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get(uploadPath, imageName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ (ví dụ: ghi log)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}