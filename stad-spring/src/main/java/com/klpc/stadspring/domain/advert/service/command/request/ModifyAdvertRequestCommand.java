package com.klpc.stadspring.domain.advert.service.command.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ModifyAdvertRequestCommand {

    private Long advertId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private String directVideoUrl;
    private String bannerImgUrl;
    private List<Long> selectedContentList;
    private String advertBannerImgUrl;
    private String category;

}
