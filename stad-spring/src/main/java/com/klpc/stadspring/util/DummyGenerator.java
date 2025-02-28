package com.klpc.stadspring.util;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advert.service.AdvertService;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.orders.service.OrdersService;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrderRequestCommand;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.ProductServiceImpl;
import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.domain.productType.service.ProductTypeService;
import com.klpc.stadspring.domain.productType.service.command.AddProductTypeCommand;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.domain.user.service.UserService;
import com.klpc.stadspring.domain.user.service.command.JoinCompanyUserCommand;
import com.klpc.stadspring.domain.user.service.command.JoinUserCommand;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyGenerator {

    private final UserService userService;
    private final AdvertService advertService;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final ProductServiceImpl productService;
    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;
    private final ContentConceptRepository contentConceptRepository;
    private final OrdersService ordersService;

//    @PostConstruct
    public void createDummy(){
        User normalUser = createDummyUsers();
        createDummyCompanyUsers();
        User companyUser1 = userRepository.findByEmail("ssafyCompany@ssafy.com").get();
        User companyUser2 = userRepository.findByEmail("ssammu@ssammu.com").get();

        List<Long> contentList = new ArrayList<>();
        List<ContentConcept> all = contentConceptRepository.findAll();
        for(int i = 0; i<all.size(); i++){
            if(i>2)
                break;
            contentList.add(all.get(i).getId());
        }
        createDummyAdvert(companyUser1,companyUser2,contentList);
        List<Advert> allAdvertByUser = advertRepository.findAllByUser(companyUser2);
        ProductType dummyProductType = null;
        if(!allAdvertByUser.isEmpty())
            dummyProductType = createDummyProduct(allAdvertByUser.get(0));

        assert dummyProductType != null;
        addOrders(normalUser,dummyProductType,contentList.get(0),allAdvertByUser.get(0).getId());
    }

    /**
     * 유저 생성
     */
    public User createDummyUsers(){
        log.info("creatDummyUsers");
        JoinUserCommand command1 = JoinUserCommand.builder()
                .nickname("king")
                .name("king")
                .email("king@king.com")
                .profileImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrQlbiMWQGo-CnCfxyV8R6bCvJeE0_Obfy4qiQeSN6uQ&s")
                .googleAT("")
                .build();
        JoinUserCommand command2 = JoinUserCommand.builder()
                .nickname("Queen")
                .name("Queen")
                .email("Queen@Queen.com")
                .profileImage("https://people.com/thmb/YIww0u4m8icR9vnhychZVxZecFs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():focal(701x0:703x2)/freddie-mercury-3-f480f4ef58b145c7871f123c8b2d3aae.jpg")
                .googleAT("")
                .build();
        JoinUserCommand command3 = JoinUserCommand.builder()
                .nickname("Pawn")
                .name("Pawn")
                .email("Pawn@Pawn.com")
                .profileImage("https://thechessworld.com/wp-content/uploads/2012/08/pawn1.jpg")
                .googleAT("")
                .build();
        userService.joinMember(command1);
        userService.joinMember(command2);
        return userService.joinMember(command3);
    }

    /**
     * 기업 회원 가입
     */
    public void createDummyCompanyUsers(){
        log.info("createDummyCompanyUsers");
        JoinCompanyUserCommand command1 = JoinCompanyUserCommand.builder()
                .email("ssafyCompany@ssafy.com")
                .name("ssafyCompany")
                .phone("010-0101-0101")
                .password("1234qwer")
                .company("ssafy")
                .comNo("1111")
                .department("B206")
                .build();
        JoinCompanyUserCommand command2 = JoinCompanyUserCommand.builder()
                .email("ssammu@ssammu.com")
                .name("ssammu")
                .phone("010-1234-1234")
                .password("1234qwer")
                .company("ssammu")
                .comNo("1234")
                .department("meet")
                .build();
        userService.JoinCompanyUser(command1);
        userService.JoinCompanyUser(command2);
    }

    /**
     * 광고 추가
     */
    public void createDummyAdvert(User user1, User user2, List<Long> contentList){

        List<String> advertVideoUrlList1 = new ArrayList<>();
        advertVideoUrlList1.add("https://ssafy-stad.s3.ap-northeast-2.amazonaws.com/AdvertVideo/71cc0506891f4de4aa5bc28389e971c9videoList");

        AddAdvertRequestCommand command1 = AddAdvertRequestCommand.builder()
                .userId(user1.getId())
                .title("유산균 김치")
                .description("발효된 Super Food 김치")
                .startDate(LocalDateTime.parse("2024-04-25T00:00:00"))
                .endDate(LocalDateTime.parse("2025-04-25T00:00:00"))
                .type("NOTPRODUCT")
                .directVideoUrl("")
                .bannerImgUrl("https://contents.codetree.ai/homepage/images/company/SSAFY_logo.png")
                .selectedContentList(contentList)
                .advertVideoUrlList(advertVideoUrlList1)
                .category("개발")
                .build();


        List<Long> contentList2 = new ArrayList<>();

        List<String> advertVideoUrlList2 = new ArrayList<>();
        advertVideoUrlList2.add("https://ssafy-stad.s3.ap-northeast-2.amazonaws.com/AdvertVideo/71cc0506891f4de4aa5bc28389e971c9videoList");

        AddAdvertRequestCommand command2 = AddAdvertRequestCommand.builder()
                .userId(user2.getId())
                .title("싸피 12기")
                .description("개발자로 취업할 수 있는 절호의 기회")
                .startDate(LocalDateTime.parse("2024-04-25T00:00:00"))
                .endDate(LocalDateTime.parse("2025-04-25T00:00:00"))
                .type("product")
                .directVideoUrl("")
                .bannerImgUrl("https://img.khan.co.kr/lady/r/1100xX/2023/03/08/news-p.v1.20230308.9abb9311c8ee43c6b181dd72e08fa534.png")
                .selectedContentList(contentList2)
                .advertVideoUrlList(advertVideoUrlList2)
                .category("푸드")
                .build();

        advertService.addAdvert(command1);
        advertService.addAdvert(command2);
    }

    /**
     * 상품 생성
     * @param advert
     */
    public ProductType createDummyProduct(Advert advert){

        Product kimchi = Product.createNewProduct(
                advert,
                "금비김치",
                2500L,
                5000L,
                LocalDateTime.parse("2024-04-25T00:00:00"),
                LocalDateTime.parse("2025-04-25T00:00:00")
        );

        Product kim = productRepository.save(kimchi);


        AddProductTypeCommand command1 = AddProductTypeCommand.builder()
                .productId(kim.getId())
                .name("금비김치")
                .price(25000L)
                .quantity(50L)
                .build();

        AddProductTypeCommand command2 = AddProductTypeCommand.builder()
                .productId(kim.getId())
                .name("은비김치")
                .price(20000L)
                .quantity(50L)
                .build();

        productTypeService.addProductType(command1);
        return productTypeService.addProductType(command2);
    }

    public void addOrders(User user, ProductType productType, Long contentId, Long advertId){
        AddOrderRequestCommand command1 = AddOrderRequestCommand.builder()
                .userId(user.getId())
                .productTypeId(productType.getId())
                .productTypeCnt(Math.max(productType.getQuantity()-10,1))
                .contentId(contentId)
                .advertId(advertId)
                .name("이태경")
                .phoneNumber("010-4124-2572")
                .locationName("집")
                .location("대전광역시 유성구 구암동678-2")
                .locationDetail("206호")
                .locationNum("46271")
                .build();
        ordersService.addOrders(command1);
    }

}
