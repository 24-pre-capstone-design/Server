package com.pre_capstone_design_24.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodStatus {
    SALE("SALE", "판매 중"),
    SOLD_OUT("SOLD_OUT", "품절"),
    SECRET("SECRET", "비공개"),
    DELETED("DELETED", "삭제됨");

    private String status;

    private String description;
}
