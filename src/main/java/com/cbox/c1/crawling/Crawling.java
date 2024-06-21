package com.cbox.c1.crawling;

import com.cbox.c1.dto.CrawlingDTO;

import com.cbox.c1.dto.CrawlingEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
public class Crawling {

    List<CrawlingDTO> dtos = new ArrayList<>();

    private WebDriver driver;
    String chromeDriver = "webdriver.chrome.driver";
    String chromePath = "C:\\chromedriver_win32\\chromedriver.exe";
    ChromeOptions options = new ChromeOptions();


    public void Chrome() {
        System.setProperty(chromeDriver, chromePath);

       options.addArguments("--headless=new");
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
//        options.addArguments("--window-size=1920,1080");
        options.setCapability("ignoreProtectedModeSettings", true);
        // weDriver 생성.
        driver = new ChromeDriver(options);
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info(driver);

    }
    public List<CrawlingDTO> crawlingMac(String url) {
        int a = 0;

        driver.get(url);
//
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String[] hUrl = {"/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[2]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[3]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[4]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[5]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[6]/a"};
        // 메뉴선택
        for (String s : hUrl) {
            WebElement menu = driver.findElement(By.xpath(s));
            log.info(menu.getText());
            menu.click();

            List<WebElement> forPname = driver.findElements(By.className("product-title"));
            List<WebElement> forPrice = driver.findElements(By.className("starting-price"));
            List<WebElement> forImg = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class = img-block]")));


            // 상품 저장용
            for (int j = 0; j < forPname.size(); j++) {
                String imgUrl = forImg.get(j).getAttribute("src");
                log.info(imgUrl);
                String pname = forPname.get(j).getText();
                String brand = "mcdonalds";

                String fileName = DownloadImg(imgUrl,pname,brand);
                int intPrice = Integer.parseInt(forPrice.get(j).getText().replaceAll("[^0-9]", ""));



                CrawlingDTO dto = new CrawlingDTO();
                dto.setCno(a += 1);
                dto.setPname(pname);
                dto.setPrice(intPrice);
                dto.setBrand(brand);
                dto.setFileName(fileName);
                dtos.add(dto);
            }

            int productCount = dtos.size();
            log.info("=============================");
            log.info("=============================");
            log.info(productCount);
            log.info("=============================");

            // 각 메뉴마다 새로운 요소들만 저장되도록 clear()
            forPname.clear();
            forPrice.clear();
        }

//        for (CrawlingDTO dto : dtos) {
//            log.info(dto.getCno() + " " + dto.getBrand() + " " + dto.getPname() + " " + dto.getPrice());
//            log.info("Kcal : "+dto.getPKcal());
//        }
        driver.quit();
        return dtos;

    }
    public List<CrawlingDTO> crawlingLotte(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // 페이지 불러오는 여유시간.
        //롯데리아 크롤링
        String[] menu = {"DCT_0000000000001153", "DCT_0000000000001154", "DCT_0000000000001155", "DCT_0000000000001156", "DCT_0000000000001157"};

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int a = 0;
        for (String s : menu) {

            WebElement button = driver.findElement(By.id(s));
            log.info(button.getText());
            button.click();

            List<WebElement> forPname = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='prod-tit']")));
            List<WebElement> forPrice = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='productList']/div/ul/li/div[2]/div[2]/span/span[1]")));
            List<WebElement> forImage = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"productList\"]/div/ul/li/div[1]/div[1]")));


            // dto
            for (int j = 0; j < forPname.size(); j++) {
                String imgVal = forImage.get(j).getCssValue("background-image");
                String imgUrl = imgVal.substring(imgVal.indexOf("\"") + 1, imgVal.lastIndexOf("\""));
                log.info(imgUrl);
                String brand = "lotteria";
                String pname = forPname.get(j).getText();
                String fileName = DownloadImg(imgUrl,pname,brand);

                CrawlingDTO dto = new CrawlingDTO();

                int intPrice = Integer.parseInt(forPrice.get(j).getText().replace(",", ""));

                dto.setCno(a += 1);
                dto.setPname(pname);
                dto.setPrice(intPrice);
                dto.setBrand(brand);
                dto.setFileName(fileName);
                dtos.add(dto);
            }

            int productCount = dtos.size();
            log.info("=============================");
            log.info("=============================");
            log.info(productCount);
            log.info("=============================");

            // 각 메뉴마다 새로운 요소들만 저장되도록 clear()
            forPname.clear();
            forPrice.clear();
        }
//        for (CrawlingDTO dto : dtos) {
//            log.info(dto.getCno()+ " "+dto.getBrand()+" "+dto.getPname() +" "+ dto.getPrice());
//        }
        driver.quit();
        return dtos;
    }

    public List<CrawlingDTO> momsCrawling(String url){
        String brand = "momstouch";
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // 페이지 불러오는 여유시간.

        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#entryIframe")));
        WebElement test1 = driver.findElement(By.className("fvwqf"));
        log.info(test1.getText());
        test1.click();

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));  // 페이지 불러오는 여유시간.

        List<WebElement> buttons2 = driver.findElements(By.xpath("//a[@class = 'fvwqf']"));


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // 페이지 불러오는 여유시간.
        buttons2.forEach(webElement -> {
            jsExecutor.executeScript("window.scrollBy(0, 500)");
            webElement.click();
        });

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> burgers = driver.findElements(By.xpath("//ul[contains(@class,'_d0Hx')]/li[not(contains(@class,'yhGu6 WhDCv'))]"));
        List<CrawlingDTO> dtos = new ArrayList<>();

        burgers.forEach(webElement -> {
            String pname = webElement.findElement(By.className("lPzHi")).getText();
            WebElement price = webElement.findElement(By.className("GXS1X"));
            String forImg = webElement.findElement(By.className("K0PDV")).getCssValue("background-image");
            String imgUrl = forImg.substring(forImg.indexOf("\"") + 1, forImg.lastIndexOf("\""));
            String fileName = DownloadImg(imgUrl,pname,brand);
            int intPrice = Integer.parseInt(price.getText().replaceAll("[^0-9]", ""));

            CrawlingDTO dto = CrawlingDTO.builder()
                    .pname(pname)
                    .price(intPrice)
                    .brand(brand)
                    .fileName(fileName)
                    .build();
            dtos.add(dto);
            log.info(pname + " __ "+intPrice);
        });

        log.info("================================");
        log.info(burgers.size());
        driver.quit();
        return dtos;

    }

    public List<CrawlingDTO> burgerKing(String url){
        List<CrawlingDTO> dtos = new ArrayList<>();
        String brand = "burgerKing";

        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#entryIframe")));

        List<WebElement> burgerList = driver.findElements(By.xpath("//div[contains(@class,'item_info')]/a[contains(@class,'info_link')]"));
        burgerList.forEach(webElement -> {
            String pname = webElement.findElement(By.className("tit")).getText();
            WebElement price = webElement.findElement(By.className("price"));
            String forImg = "";
            try{
                forImg = webElement.findElement(By.className("img")).getAttribute("src");
            }catch(Exception ignored){

            }

            String fileName = DownloadImg(forImg,pname,brand);
            int intPrice = Integer.parseInt(price.getText().replaceAll("[^0-9]", ""));

            CrawlingDTO dto = CrawlingDTO.builder()
                    .pname(pname)
                    .price(intPrice)
                    .brand(brand)
                    .fileName(fileName)
                    .build();
            dtos.add(dto);
            log.info(brand + pname + " __ "+intPrice);
            log.info(forImg);
        });
        log.info("================================");
        log.info(burgerList.size());
        driver.quit();
        return dtos;
    }

    public List<CrawlingDTO> crawlingKFC(String url){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String brand = "kfc";
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));  // 페이지 불러오는 여유시간.

        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#entryIframe")));
        jsExecutor.executeScript("window.scrollBy(0, 500)");
        WebElement test1 = driver.findElement(By.className("fvwqf"));
        log.info(test1.getText());
        test1.click();



        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> burgers = driver.findElements(By.xpath("//ul[contains(@class,'order_list_area')]/li[(contains(@class,'order_list_item'))]"));

        List<CrawlingDTO> dtos = new ArrayList<>();

        burgers.forEach(webElement -> {
            String pname = webElement.findElement(By.className("tit")).getText();
            WebElement price = webElement.findElement(By.className("price"));
            String forImg = "";
            try{
                forImg = webElement.findElement(By.className("img")).getAttribute("src");
            }catch(Exception ignored){

            }

            String fileName = DownloadImg(forImg,pname,brand);
            int intPrice = Integer.parseInt(price.getText().replaceAll("[^0-9]", ""));

            CrawlingDTO dto = CrawlingDTO.builder()
                    .pname(pname)
                    .price(intPrice)
                    .brand(brand)
                    .fileName(fileName)
                    .build();
            dtos.add(dto);
            log.info(brand + pname + " __ "+intPrice);
            log.info(forImg);
        });

        log.info("================================");
        log.info(burgers.size());
        driver.quit();
        return dtos;


    }
    public List<CrawlingEventDTO> crawlingEvent(String url) throws InterruptedException {

        // url on
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("selenium : " + driver.getTitle());
        log.info("url : " + driver.getCurrentUrl());
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        List<CrawlingEventDTO> dtos = new ArrayList<>();

        List<WebElement> testList = driver.findElements(By.xpath("//tr[contains(@class,'table_body blocktarget')]/td[contains(@class ,'subject')]/div/a[contains(@class,'deco')]"));

        List<String> listUrls = new ArrayList<>();


        testList.forEach(ele->{
            log.info(ele.getText());
            listUrls.add(ele.getAttribute("href"));
        });

        for (int i=0;i<7;i++){
            jsExecutor.executeScript("window.scrollBy(0, 200)");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            String listText = null;
            try {
                listText = testList.get(i).getText();
            } catch (Exception e) {
                String tempXpath = "//tr[contains(@class,'table_body blocktarget')]["+i+"]/td[contains(@class ,'subject')]/div/a[@class='deco']";
                log.info("xpath : " +tempXpath);
                listText = driver.findElement(By.xpath(tempXpath)).getText();
            }

            String brand = " ";
            if (listText.contains("버거킹") || listText.contains("와퍼")) {
                brand = "버거킹";
            }else if(listText.contains("맥도날드")||listText.contains("맥")){
                brand = "맥도날드";
            }else if(listText.contains("롯데리아")||listText.contains("롯데")){
                brand = "롯데리아";
            }else if(listText.contains("맘스터치")){
                brand = "맘스터치";
            }else if(listText.contains("KFC")){
                brand = "KFC";
            }


            log.info(brand);

            if(listText.endsWith("...")||listText.endsWith("~)")){
                driver.navigate().to(listUrls.get(i));

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                listText = driver.findElement(By.className("subject_inner_text")).getText();
                log.info(listText);
                driver.navigate().back();
            }
            log.info("==============================================================");
            log.info(listText);
            log.info("==============================================================");
            CrawlingEventDTO dto = CrawlingEventDTO.builder()
                    .eventInfo(listText)
                    .brand(brand)
                    .build();
            dtos.add(dto);
        }

//        for(int i=0;i< testList.size()-20;i++){
//            Thread.sleep(2000);
//            String listText = testList.get(i).getText();
//            String brand = " ";
//            if (listText.contains("버거킹") || listText.contains("와퍼")) {
//                brand = "버거킹";
//            }
//            log.info(brand);
//            try {
//                driver.navigate().to(listUrls.get(i));
//            } catch (StaleElementReferenceException e) {
//                log.info("___________________________error__________________________"+i+9);
//                jsExecutor.executeScript("window.scrollBy(0, 500)");
//                driver.navigate().to(url);
//                String xpath = "//*[@id=\"board_list\"]/div/div[2]/table/tbody/tr["+(i+9)+"]/td[3]/div/a[1]";
//                String tempUrl = driver.findElement(By.xpath(xpath)).getAttribute("href");
//
//                driver.navigate().to(tempUrl);
//            }
//
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//            log.info("----------------IN---------------");
//            List<WebElement> imgs;
//            jsExecutor.executeScript("window.scrollBy(0, 500)");
//            imgs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@class = 'img_load']/img")));
//            log.info(imgs.size());
//
//            CrawlingEventDTO dto = CrawlingEventDTO.builder()
//
//                    .build();
//
//            driver.navigate().back();
//            log.info("---------back-------");
//        } // for end

        driver.quit();
        return dtos;

    }





    public void Crawlingimg(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // 페이지 불러오는 여유시간.
        WebElement button = driver.findElement(By.id("DCT_0000000000001153"));
        log.info(button.getText());
        button.click();

        WebElement burger = driver.findElement(By.xpath("//*[@id=\"productList\"]/div/ul/li[1]/a"));
        burger.click();
        String btext = driver.findElement(By.className("btext")).getText();
        log.info(btext);
        driver.findElement(By.className("btn-fold")).click();


        String imgVal = driver.findElement(By.cssSelector("div.thumb-img")).getCssValue("background-image");
        log.info(imgVal);
        String imgUrl = imgVal.substring(imgVal.indexOf("\"") + 1, imgVal.lastIndexOf("\""));
        log.info(imgUrl);
    }


    public String DownloadImg(String imgUrl ,String originalFileName,String brand){

        String path = "C:\\webserver2\\nginx-1.24.0\\html\\"+brand;

        if (imgUrl == null || imgUrl.length()==0){
            return "";
        }

        try {
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            String uuid = UUID.randomUUID().toString();

            String fileName = uuid+"_"+originalFileName+".png";
            String fullPath = path+ File.separator+fileName;

            OutputStream out = new FileOutputStream(fullPath);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            in.close();

            log.info("DownLoad DONE");
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
