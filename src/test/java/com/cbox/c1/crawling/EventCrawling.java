package com.cbox.c1.crawling;

import com.cbox.c1.dto.CrawlingEventDTO;
import com.cbox.c1.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@Log4j2

public class EventCrawling {

    @Autowired
    private EventRepository eventRepository;

    Crawling crawling = new Crawling();

    @Test
    public void eventInsertTest() throws InterruptedException {
        String url = "https://bbs.ruliweb.com/market/board/1020?search_type=subject&search_key=%EB%B2%84%EA%B1%B0%ED%82%B9&cate=12";
        crawling.Chrome();
        List<CrawlingEventDTO> events = crawling.CrawlingEvent(url);
        events.forEach(event -> log.info(event.getPEno()+" _ "+event.getBrand()+" _ "+event.getEventInfo()));


    }



    public String eventParsing(String eventInfo){
        String result = null;
        String datePattern = "\\(\\d{1,2}/\\d{1,2}~\\d{1,2}/\\d{1,2}\\)"; // 날짜 형식: (7/24~8/6)
        String pricePattern = "\\d+(,\\d+)?[원]"; // 가격 형식: 6천원, 2,500원, 5,600원 등

        String date = null;
        String price = null;

        // 날짜 추출
        Pattern dateRegex = Pattern.compile(datePattern);
        Matcher dateMatcher = dateRegex.matcher(eventInfo);
        if (dateMatcher.find()) {
            date = dateMatcher.group().replaceAll("[()]", "");
        }

        // 가격 추출
        Pattern priceRegex = Pattern.compile(pricePattern);
        Matcher priceMatcher = priceRegex.matcher(eventInfo);
        if (priceMatcher.find()) {
            price = priceMatcher.group();
        }
        result = date+price;

        return result;

    }

}
