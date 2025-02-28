package com.klpc.stadspring.domain.cart.service.command;

import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class GetCartProductCommand {
    private ProductType productType;
    private User user;
    private Long quantity;
    private Long advertId;
    private Long contentId;
}
