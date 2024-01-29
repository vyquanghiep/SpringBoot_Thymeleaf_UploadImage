package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void saveProduct(Product product) {
        try {
            MultipartFile imageFile = product.getImageFile();
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, fileName);
            Files.write(filePath, imageFile.getBytes());

            product.setImagePath(fileName);  // Không thêm '/uploads/' vào đường dẫn

            // Kiểm tra xem có ID hay không để phân biệt giữa lưu mới và cập nhật
            if (product.getId() != null) {
                // Nếu có ID, đây là cập nhật, xoá ảnh cũ trước khi lưu ảnh mới
                deleteOldImage(productRepository.findById(product.getId()).map(Product::getImagePath).orElse(null));
            }

            productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteOldImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path oldFilePath = Paths.get(uploadPath, imagePath);
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
