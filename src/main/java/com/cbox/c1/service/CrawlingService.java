package com.cbox.c1.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cbox.c1.dto.CrawlingDTO;
import com.cbox.c1.dto.CrawlingEventDTO;

@Transactional
public interface CrawlingService {


    List<CrawlingDTO> getCrawlingProducts(String url, String category);

    List<CrawlingEventDTO> getCrawlingEvents(String urlString, String category);
    
    
}
