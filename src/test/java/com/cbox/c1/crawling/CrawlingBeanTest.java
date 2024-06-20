package com.cbox.c1.crawling;


import com.cbox.c1.dto.CrawlingDTO;
import com.cbox.c1.entity.Product;
import com.cbox.c1.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class CrawlingBeanTest {
    @Autowired
    private ProductRepository productRepository;

    Crawling crawling = new Crawling();


    @Test
    public void CrawlingMacProductTestInsert(){
        String url = "https://www.mcdelivery.co.kr/kr/browse/menu.html?daypartId=1&catId=10";
        crawling.Chrome();
        List<CrawlingDTO> mac = crawling.crawlingMac(url);
        for (CrawlingDTO dto : mac) {
            Product product = Product.builder()
                    .pname(dto.getPname())
                    .price(dto.getPrice())
                    .brand(dto.getBrand())
                    .fileName(dto.getFileName())
                    .build();
            productRepository.save(product);
        }
    }
    @Test
    public void CrawlingKfcTest(){
        String url = "https://map.naver.com/v5/search/KFC%20%EA%B4%91%ED%99%94%EB%AC%B8%EC%A0%90/place/11808181?c=15,0,0,0,dh&isCorrectAnswer=true";

        crawling.Chrome();
        List<CrawlingDTO> kfc = crawling.crawlingKFC(url);
        for (CrawlingDTO dto : kfc){
            Product product = Product.builder()
                    .pname(dto.getPname())
                    .price(dto.getPrice())
                    .brand(dto.getBrand())
                    .fileName(dto.getFileName())
                    .build();
            productRepository.save(product);
        }


    }

@Test
    public void CrawlingLotteProductTestInsert(){
        String url = "https://www.lotteeatz.com/brand/ria";
        crawling.Chrome();
        List<CrawlingDTO> lotte = crawling.crawlingLotte(url);
        for (CrawlingDTO dto : lotte){
            Product product = Product.builder()
                    .pname(dto.getPname())
                    .price(dto.getPrice())
                    .brand(dto.getBrand())
                    .fileName(dto.getFileName())
                    .build();
            productRepository.save(product);
        }

    }

    @Test
    public void burgerKingCrawlingTest(){
        String url = "https://map.naver.com/v5/search/%EB%B2%84%EA%B1%B0%ED%82%B9/place/11782345?c=15,0,0,0,dh&placePath=%2FbookingDeliveryItem";
        crawling.Chrome();
        List<CrawlingDTO> king = crawling.burgerKing(url);
        king.forEach(dto -> {
            Product product = Product.builder()
                    .pname(dto.getPname())
                    .price(dto.getPrice())
                    .brand(dto.getBrand())
                    .fileName(dto.getFileName())
                    .build();
            productRepository.save(product);
        });
    }

    @Test
    public void burgerKing(){
        String brand = "burgerKing";
        String[] burger = {"DIABLO IV 스페셜 세트" ,
                "바삭킹&너겟킹10" ,
                "바삭킹&모짜볼10" ,
                "프리미엄팩 1" ,
                "헬로 디아블로 와퍼" ,
                "헬로 이나리우스 와퍼" ,
                "헬로 릴리트 와퍼" ,
                "치킨킹BLT" ,
                "치킨킹" ,
                "더블비프불고기버거" ,
                "블랙바비큐콰트로치즈와퍼" ,
                "블랙바비큐와퍼" ,
                "몬스터와퍼" ,
                "몬스터X" ,
                "콰트로치즈와퍼" ,
                "통새우와퍼" ,
                "치즈와퍼" ,
                "갈릭불고기와퍼" ,
                "불고기와퍼" ,
                "와퍼" ,
                "통새우와퍼주니어" ,
                "콰트로치즈와퍼주니어" ,
                "치즈와퍼주니어" ,
                "불고기와퍼주니어" ,
                "와퍼주니어" ,
                "비프불고기버거" ,
                "치즈버거" ,
                "비프&슈림프버거" ,
                "통새우슈림프버거" ,
                "슈림프버거" ,
                "롱치킨버거" ,
                "치킨버거" ,
                "바비큐치킨버거" ,
                "해쉬브라운" ,
                "너겟킹" ,
                "21치즈스틱" ,
                "어니언링" ,
                "바삭킹" ,
                "바삭킹8조각,소스" ,
                "쉐이킹프라이" ,
                "크리미모짜볼" ,
                "코코넛슈림프,스위트칠리소스" ,
                "치즈프라이" ,
                "프렌치프라이" ,
                "코울슬로" ,
                "콘샐러드" ,
                "사이드소스" ,
                "시즈닝" ,
                "제로슈가에이드" ,
                "Dole 후룻컵" ,
                "아메리카노" ,
                "핫초코" ,
                "아이스초코" ,
                "코카콜라" ,
                "코카콜라 제로" ,
                "스프라이트" ,
                "스프라이트 제로" ,
                "미닛메이드 오렌지" ,
                "순수(미네랄워터)"};
        int[] prices = {12500,
                16000,
                15900,
                22000,
                14000,
                10500,
                10500,
                8400,
                7400,
                5900,
                10200,
                10200,
                10200,
                11000,
                8800,
                8800,
                8600,
                8300,
                8000,
                8000,
                6200,
                6200,
                5900,
                5600,
                5600,
                4900,
                4400,
                7900,
                6900,
                5900,
                5600,
                4600,
                4600,
                2700,
                3100,
                3400,
                3300,
                4100,
                13600,
                3300,
                3500,
                4800,
                3900,
                3000,
                3000,
                3000,
                500,
                300,
                3100,
                4800,
                2400,
                2900,
                2900,
                2900,
                2900,
                2900,
                2900,
                3700,
                2200,
        };

        for (int i=0;i< burger.length;i++){
            Product product = Product.builder()
                    .pname(burger[i])
                    .price(prices[i])
                    .brand(brand)
                    .fileName(burger[i]+".png")
                    .build();
            productRepository.save(product);

        }





    }

    @Test
    public void momsCrawlingTest(){
        String url = "https://map.naver.com/v5/search/%EB%A7%98%EC%8A%A4%ED%84%B0%EC%B9%98/place/570774588?placePath=%3Fentry=pll%26from=nx%26fromNxList=true&c=15,0,0,0,dh";
        crawling.Chrome();
        List<CrawlingDTO> moms = crawling.momsCrawling(url);
        moms.forEach(dto -> {
            Product product = Product.builder()
                    .pname(dto.getPname())
                    .price(dto.getPrice())
                    .brand(dto.getBrand())
                    .fileName(dto.getFileName())
                    .build();
            productRepository.save(product);
        });
    }



}
