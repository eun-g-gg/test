package com.klpc.stadspring.domain.product_review.controller.response;

import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdverseResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.command.ProductReviewInfoCommand;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetProductReviewListResponse {
    List<ProductReviewInfoCommand> reviewList;
}
