package com.cbox.c1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_Crowling")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    // 브랜드 이름
    private String brand;
    // 상품이름
    private String pname;
    // 가격
    private int price;
    // 이미지 파일 이름
    private String fileName;


}
