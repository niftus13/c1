package com.cbox.c1.crawling;

import com.cbox.c1.dto.CrawlingDTO;


import com.cbox.c1.repository.ProductRepository;
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

//        options.addArguments("--headless=new");
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);
        // weDriver 생성.
        driver = new ChromeDriver(options);
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info(driver);

    }
    public List<CrawlingDTO> CrawlingMac(String url) {
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
            List<WebElement> forKcal = driver.findElements(By.className("product-nutritional-info"));
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
                dto.setPKcal(forKcal.get(j).getText());
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
    public List<CrawlingDTO> CrawlingLotte(String url) {
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
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // 페이지 불러오는 여유시간.

        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#entryIframe")));
        WebElement test1 = driver.findElement(By.className("fvwqf"));
        log.info(test1.getText());
        test1.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));  // 페이지 불러오는 여유시간.
        List<WebElement> buttons2 = driver.findElements(By.xpath("//a[@class = 'fvwqf']"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // 페이지 불러오는 여유시간.
        buttons2.forEach(WebElement::click);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> burgers = driver.findElements(By.xpath("//ul[contains(@class,'_d0Hx')]/li[not(contains(@class,'yhGu6 WhDCv'))]"));
        burgers.forEach(ele -> log.info(ele.getText()));

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

        return dtos;

    }









    public void CrawlingEvent(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        log.info("selenium : " + driver.getTitle());
        log.info("url : " + driver.getCurrentUrl());

        WebElement test = driver.findElement(By.xpath("//button[contains(@class ,'btn_ruli_dark')]"));
        log.info(test.getText());
        log.info(test.getClass());




        List<WebElement> testList = driver.findElements(By.xpath("//*[@class = 'table_body blocktarget']/td[3]/div/a[1]"));
        for (WebElement webElement : testList) {

            String listText = webElement.getText();
            String brand = listText.substring(listText.indexOf("[") + 1, listText.indexOf("]"));
            String brand1 = " ";
            if (listText.contains("버거킹") || listText.contains("와퍼")) {
                brand1 = "버거킹";
            }
            String eventInfo = listText.substring(listText.indexOf("]") + 2);


            log.info(brand);
            log.info(brand1);
            log.info(listText);
            log.info(eventInfo);

            log.info("-----------------------------");


        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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

    public static class DownloadException extends RuntimeException{
        public DownloadException(String msg){
            super(msg);
        }
    }
    public String DownloadImg(String imgUrl ,String originalFileName,String brand){

        String path = "C:\\webserver2\\nginx-1.24.0\\html\\"+brand;


        if (imgUrl == null || imgUrl.length()==0){
            throw new DownloadException("Url is NULL");
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
