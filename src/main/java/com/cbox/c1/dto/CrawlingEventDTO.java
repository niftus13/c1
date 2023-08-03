package com.cbox.c1.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrawlingEventDTO {

    // id
    private Integer pEno;
    // 할인정보 (나눌 필요가 있음)
    private String eventInfo;

    // 카테고리 : 브랜드
    private String brand;


}
