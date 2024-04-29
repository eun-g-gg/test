package com.klpc.stadspring.global.auth.controller.request;

import com.klpc.stadspring.domain.user.service.command.AppLoginCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppLoginRequest {
    String email;
    String phone;
    String nickname;
    String profile;
    String googleAT;

    public AppLoginCommand toCommand(){
        if (profile == null){
            profile = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
        }
        return AppLoginCommand.builder()
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .profile(profile)
                .googleAT(googleAT)
                .build();
    }
}
