package com.klpc.stadspring.domain.product_review.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteReviewCommand {
    Long id;
}
