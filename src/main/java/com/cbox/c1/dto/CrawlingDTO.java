package com.cbox.c1.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrawlingDTO {


    private Integer cno;
    private String brand;
    private String pname;
    private int price;
    private String pdesc;
    private String pKcal;
    private String fileName;


}
