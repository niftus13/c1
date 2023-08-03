package com.cbox.c1.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrawlingDTO {

    //PK
    private Integer cno;

    // 브랜드 이름
    private String brand;

    // 상품이름
    private String pname;

    // 가격
    private int price;

    // 이미지 파일 이름
    private String fileName;



}
