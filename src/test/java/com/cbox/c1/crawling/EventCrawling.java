package com.cbox.c1.crawling;

import com.cbox.c1.dto.CrawlingEventDTO;
import com.cbox.c1.repository.EventRepository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        events.forEach(event -> {
            log.info(event.getPEno()+" _ "+event.getBrand()+" _ "+event.getEventInfo());
            String eventInfo = event.getEventInfo().substring(
                    event.getEventInfo().indexOf("]")+1,
                    event.getEventInfo().indexOf("(")
            );
            String eventDate = event.getEventInfo().substring(
                    event.getEventInfo().indexOf("(")
            );

            log.info(eventInfo +" "+eventDate);
            LocalDate[] dates = parseDate(eventDate);

            log.info("startDate : "+dates[0]);
            log.info("endDate : "+dates[1]);
            log.info("=================dto end=========================");

        });


    }

    public static LocalDate[] parseDate(String dateStr) {
        String dateFormat = "M/d";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        Pattern pattern = Pattern.compile("(\\d{1,2}/\\d{1,2})~(\\d{1,2})(/\\d{1,2})?");
        Matcher matcher = pattern.matcher(dateStr);

        LocalDate today = LocalDate.now();
        int year = today.getYear();


        if (matcher.find()) {
            String startDateStr = matcher.group(1);
            String endDateStr = matcher.group(2);
            log.info("-------ParseDate-------------");
            log.info(endDateStr);

            int startDateMonth = Integer.parseInt(startDateStr.split("/")[0]);
            int startDateDay = Integer.parseInt(startDateStr.split("/")[1]);

            // endDateStr에 "/" 포함
            int endDateMonth = startDateMonth;
            int endDateDay;

            if (endDateStr.contains("/")) {
                endDateMonth = Integer.parseInt(endDateStr.split("/")[0]);
                endDateDay = Integer.parseInt(endDateStr.split("/")[1]);
            } else {
                // endDateStr에 "/" 미포함
                endDateDay = Integer.parseInt(endDateStr);
            }

            LocalDate startDate = LocalDate.of(year, startDateMonth, startDateDay);
            LocalDate endDate = LocalDate.of(year, endDateMonth, endDateDay);

            return new LocalDate[]{startDate, endDate};
        }

        return null;
    }


}
