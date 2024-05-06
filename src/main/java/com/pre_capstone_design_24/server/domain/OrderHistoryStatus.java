package com.pre_capstone_design_24.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderHistoryStatus {

        NEW("NEW", "신규주문"),
        COOKING("COOKING", "조리중"),
        COOKING_COMPLETE("COOKING_COMPLETE", "조리완료"),
        PAYMENT_COMPLETE("PAYMENT_COMPLETE", "결제완료");

        private String status;

        private String description;

}
