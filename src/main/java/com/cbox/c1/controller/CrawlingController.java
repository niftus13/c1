package com.cbox.c1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.batch.core.job.builder.JobBuilderException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbox.c1.dto.CrawlingDTO;
import com.cbox.c1.service.CrawlingService;


@RestController
@Log4j2
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;


    
    @Scheduled(cron = "0 0 0 1 * *") // 매월 1월 1일 실행
    public void batchCrawling() throws JobBuilderException {
        log.info("매월 1월 1일 실행");
        crawlingService.getCrawlingProducts("https://www.mcdelivery.co.kr/kr/browse/menu.html?daypartId=1&catId=10", "mac");
        crawlingService.getCrawlingProducts("https://www.lotteeatz.com/brand/ria", "lotte");
        crawlingService.getCrawlingProducts("https://map.naver.com/v5/search/%EB%B2%84%EA%B1%B0%ED%82%B9/place/11782345?c=15,0,0,0,dh&placePath=%2FbookingDeliveryItem", "burgerKing");
        crawlingService.getCrawlingProducts("https://map.naver.com/v5/search/%EB%A7%98%EC%8A%A4%ED%84%B0%EC%B9%98/place/570774588?placePath=%3Fentry=pll%26from=nx%26fromNxList=true&c=15,0,0,0,dh", "moms");


    }
    




}
