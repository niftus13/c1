package com.cbox.c1.dto;

import lombok.*;

import java.io.File;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrawlingDTO {


    private Integer cno;
    private String pfran;
    private String pname;
    private int price;
    private String pdesc;
    Map<String, String> pinfo;
    private String pal;

    private String pFname;


}
