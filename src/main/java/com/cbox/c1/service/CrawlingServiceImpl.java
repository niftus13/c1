package com.cbox.c1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cbox.c1.crawling.Crawling;
import com.cbox.c1.dto.CrawlingDTO;
import com.cbox.c1.dto.CrawlingEventDTO;
import com.cbox.c1.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CrawlingServiceImpl implements CrawlingService{

    private final ProductRepository productRepository;
    private final Crawling crawling;

    @Override
    public List<CrawlingDTO> getCrawlingProducts(String url, String category) {
        log.info(url);
        log.info(".....................");
        log.info(category);

        List<CrawlingDTO> dtos = null;

        if (category!=null && url != null){

            crawling.Chrome();
            switch (category) {
                case "mac":
                    dtos = crawling.crawlingMac(url);
                    break;
                case "lotte":
                    dtos = crawling.crawlingLotte(url);
                    break;
                case "burgerking":
                    dtos = crawling.burgerKing(url);
                    break;
                case "kfc":
                    dtos = crawling.crawlingKFC(url);
                    break;
                case "moms":
                    dtos = crawling.momsCrawling(url);
                    break;
                default:
                    // Handle unknown category if necessary
                    break;
            }

        }
        return dtos;

    }

    @Override
    public List<CrawlingEventDTO> getCrawlingEvents(String urlString, String category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrawlingEvents'");
    }

}
