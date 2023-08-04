package com.cbox.c1.crawling;

import com.cbox.c1.dto.CrawlingEventDTO;
import com.cbox.c1.entity.Event;
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
        String urlMac = "https://bbs.ruliweb.com/market/board/1020?search_type=subject&search_key=%EB%A7%A5%EB%8F%84%EB%82%A0%EB%93%9C%EC%95%B1";
        String urlLotte = "https://bbs.ruliweb.com/market/board/1020?search_type=subject&search_key=%EB%A1%AF%EB%8D%B0%EB%A6%AC%EC%95%84";
        String urlMoms = "https://bbs.ruliweb.com/market/board/1020?search_type=subject&search_key=%EB%A7%98%EC%8A%A4%ED%84%B0%EC%B9%98";
        String urlKfc = "https://bbs.ruliweb.com/market/board/1020?search_type=subject&search_key=KFC";
        crawling.Chrome();
        List<CrawlingEventDTO> events = crawling.CrawlingEvent(urlKfc);

        for (CrawlingEventDTO dto : events){

            String eventInfo = null;
            try {
                eventInfo = dto.getEventInfo().substring(
                        dto.getEventInfo().indexOf("]")+1,
                        dto.getEventInfo().indexOf("(")
                );
            } catch (Exception e) {
                continue;
            }

            log.info(eventInfo);

            String eventDate = dto.getEventInfo().substring(
                    dto.getEventInfo().indexOf("(")
            );
            log.info(eventDate);

            String dateTarget = convertToRangeFormat(eventDate);
            log.info(dateTarget);

            String brand = dto.getBrand();

            LocalDate[] dates = parseDate(dateTarget);
                        log.info("brand : " + brand);
            try {
                log.info("eventInfo : "+eventInfo);
                log.info("eventDate : "+eventDate);
                log.info("startDate : "+dates[0]);
                log.info("endDate : "+dates[1]);
                log.info("=================dto end=========================");
            } catch (Exception e) {
                continue;
            }
            Event event = Event.builder()
                    .eventInfo(eventInfo)
                    .brand(brand)
                    .startDate(dates[0])
                    .endDate(dates[1])
                    .build();
            eventRepository.save(event);
        };


    }

    @Test
    public void parseDateTest(){
        String testDate = "(7/20~)";
        if(testDate.matches("\\((~?\\d{1,2})/(\\d{1,2}~?)\\)")){
            log.info("========matches===============");
        }
        String convertDate = convertToRangeFormat(testDate);
        log.info(convertDate);

        LocalDate[] dates = parseDate(convertDate);
        log.info(dates[0]+" : "+dates[1]);
    }
    public static String convertToRangeFormat(String input) {
        String regex = "\\((~?\\d{1,2}/\\d{1,2}~?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String date = matcher.group(1).replace("~","");
            return "(" + date + "~" + date + ")";
        }
        return input;
    }


    public static LocalDate[] parseDate(String dateStr) {
        String dateFormat = "M/d";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        log.info(dateStr + " : data");

        Pattern pattern = Pattern.compile("(\\d{1,2}/\\d{1,2})~(\\d{1,2})(/\\d{1,2})?");

        Matcher matcher = pattern.matcher(dateStr);

        LocalDate today = LocalDate.now();
        int year = today.getYear();


        if (matcher.find()) {
            String startDateStr = matcher.group(1);
//            log.info("======StartDateStr======= : "+startDateStr);
            String endDateStr = matcher.group(2);
//            log.info("======endDateStr======= : "+endDateStr);

            int startDateMonth = Integer.parseInt(startDateStr.split("/")[0]);
//            log.info("======startDateMonth======= : "+startDateMonth);

            int startDateDay = Integer.parseInt(startDateStr.split("/")[1]);
//            log.info("======startDateDay======= : "+startDateDay);

            int endDateMonth = startDateMonth;
            int endDateDay = Integer.parseInt(endDateStr);
//            log.info("======endDateDay ======= : "+endDateDay);

            if(matcher.group(3) != null){
                endDateMonth = Integer.parseInt(endDateStr);
//            log.info("======endDateMonth ======= : "+endDateMonth);
                endDateDay = Integer.parseInt(matcher.group(3).substring(1));
//            log.info("======endDateDay ======= : "+endDateDay);
            }

            LocalDate startDate = LocalDate.of(year, startDateMonth, startDateDay);
            log.info("======startDate ====== : "+ startDate);
            LocalDate endDate = LocalDate.of(year, endDateMonth, endDateDay);
            log.info("======endDate ===== : "+ endDate);
            return new LocalDate[]{startDate, endDate};
        }

        return null;
    }


}
