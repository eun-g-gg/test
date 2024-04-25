package com.klpc.stadspring.domain.contents.watched.service.command.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModifyWatchingContentCommand {
    private Long userId;
    private Long detailId;
    private boolean status;
    private String stopTime;
}
