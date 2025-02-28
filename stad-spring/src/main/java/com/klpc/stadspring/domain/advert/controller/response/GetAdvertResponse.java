package com.klpc.stadspring.domain.advert.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Builder
public class GetAdvertResponse {

    String title;
    String description;
    String startDate;
    String endDate;
    String directVideoUrl;
    String bannerImgUrl;
    String type;
    List<Long> selectedContentList;
    List<String> advertVideoUrlList;
    String category;

}
