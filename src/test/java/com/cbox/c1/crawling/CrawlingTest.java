package com.cbox.c1.crawling;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CrawlingTest {

    Crawling crawling = new Crawling();

    @Test
    public void test1 () {
        String url = "http://www.lotteria.com/menu/Menu_all.asp";
        crawling.Chrome();
        crawling.Crawlingimg(url);

    }

    @Test
    public void test2(){
        String url = "https://bbs.ruliweb.com/market/board/1020?search_type=subject&search_key=%EB%B2%84%EA%B1%B0%ED%82%B9&cate=12";
        crawling.Chrome();

        try {
            crawling.CrawlingEvent(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMac(){
        String url = "https://www.mcdelivery.co.kr/kr/browse/menu.html?daypartId=1&catId=10";
        crawling.Chrome();

        try {
            crawling.CrawlingMac(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void lotte(){

        String url = "http://www.lotteria.com/menu/Menu_all.asp";
        crawling.Chrome();
        crawling.CrawlingLotte(url);


    }



}
