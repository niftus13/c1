package com.cbox.c1.crawling;

import com.cbox.c1.dto.CrawlingDTO;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;



@Log4j2
public class Crawling {


    List<CrawlingDTO> dtos = new ArrayList<CrawlingDTO>();

    private WebDriver driver;
    String chromeDriver = "webdriver.chrome.driver";
    String chromePath = "C:\\chromedriver_win32\\chromedriver.exe";
    ChromeOptions options = new ChromeOptions();


    public void Chrome() {
        System.setProperty(chromeDriver,chromePath);

//        options.setHeadless(true);
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

    public void Crawling(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        //롯데리아 크롤링
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());

        log.info("++++++++++++++++++++ URL : " + driver.getCurrentUrl());



        String[] menu = {"DCT_0000000000001153", "DCT_0000000000001154","DCT_0000000000001155","DCT_0000000000001156","DCT_0000000000001157"};

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> pname = new ArrayList<>();
        List<WebElement> price = new ArrayList<>();
        int a = 0;
            for (int i=0; i < menu.length; i++) {

                WebElement button = driver.findElement(By.id(menu[i]));
                log.info(button.getText());
                button.click();

                List<WebElement> forPname = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='prod-tit']")));
                List<WebElement> forPrice = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='productList']/div/ul/li/div[2]/div[2]/span/span[1]")));

                // dto
                for (int j = 0; j < forPname.size(); j++) {

                    CrawlingDTO dto = new CrawlingDTO();

                    int intPrice = Integer.parseInt(forPrice.get(j).getText().replace(",", ""));
                    dto.setCno(a+=1);
                    dto.setPname(forPname.get(j).getText());
                    dto.setPrice(intPrice);

                    dtos.add(dto);
                }

                int productCount = dtos.size();
                log.info("=============================");
                log.info("=============================");
                log.info(productCount );
                log.info("=============================");

                // 각 메뉴마다 새로운 요소들만 저장되도록 clear()
                forPname.clear();
                forPrice.clear();
            }
        for (CrawlingDTO dto: dtos) {
            log.info(dto.getCno()+dto.getPname()+" "+dto.getPrice());
        }
        driver.quit();
    }

    public void CrawlingEvent(String url){
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        log.info("selenium : " + driver.getTitle());
        log.info("url : " + driver.getCurrentUrl());

        WebElement test = driver.findElement(By.xpath("//button[contains(@class ,'btn_ruli_dark')]"));
        log.info(test.getText());
        log.info(test.getClass());

        List<WebElement> testList = driver.findElements(By.xpath("//a[contains(@class,'deco')]"));
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        for(WebElement list : testList){
            log.info(list);
            log.info("-----------------------------");
            log.info(list.getText());
            log.info("-----------------------------");
        }

        WebElement sample = driver.findElement(By.xpath("//*[@id=\"board_list\"]/div/div[2]/table/tbody/tr[7]/td[3]/div/a[1]"));
        log.info(sample.getAttribute("href"));
        log.info(sample.getAriaRole());

        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

    }

    public void Crawlingimg(String url){
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
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

    public void CrawlingMac(String url){
        int a=0;

        driver.get(url);
        List<WebElement> pname = new ArrayList<>();
        List<WebElement> price = new ArrayList<>();

        String[] hUrl = {"/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[2]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[3]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[4]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[5]/a",
                "/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/ul/li[1]/ul/li[6]/a"};

        for(int i=0; i<hUrl.length;i++){
            WebElement menu = driver.findElement(By.xpath(hUrl[i]));
            log.info(menu.getText());
            menu.click();

            List<WebElement> forPname = driver.findElements(By.className("product-title"));
            List<WebElement> forPrice = driver.findElements(By.className("starting-price"));
            List<WebElement> forDesc = driver.findElements(By.xpath("//div[contains(@class,'popover-wrapper')]/div"));
            log.info(forDesc.get(1).getText());


            for (int j = 0; j < forPname.size(); j++) {

                CrawlingDTO dto = new CrawlingDTO();
                int intPrice = Integer.parseInt(forPrice.get(j).getText().replaceAll("[^0-9]", ""));
                dto.setCno(a+=1);
                dto.setPname(forPname.get(j).getText());
                dto.setPrice(intPrice);
                dto.setPdesc(forDesc.get(j).getText());

                dtos.add(dto);
            }

            int productCount = dtos.size();
            log.info("=============================");
            log.info("=============================");
            log.info(productCount );
            log.info("=============================");

            // 각 메뉴마다 새로운 요소들만 저장되도록 clear()
            forPname.clear();
            forPrice.clear();
        }
        for (CrawlingDTO dto: dtos) {
            log.info(dto.getCno()+dto.getPname()+" "+dto.getPrice()+" "+dto.getPdesc());
        }
        driver.quit();




    }




}
