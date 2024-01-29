package com.example;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imagePath;

    @Transient
    private MultipartFile imageFile;



}

