package com.pre_capstone_design_24.server.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Status {

    //공통 정상 응답
    OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "생성되었습니다."),

    //공통 오류 응답
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    CONFLICT(HttpStatus.CONFLICT, "COMMON409", "이미 생성되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버에 오류가 발생했습니다."),


    //Owner 오류 응답
    OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "OWNER404", "사장님이 존재하지 않습니다."),
    OWNER_PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "OWNER400", "비밀번호가 틀렸습니다."),
    OWNER_ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"OWNER400", "이미 존재하는 ID입니다."),
    OWNER_PASSWORD_IS_THE_SAME(HttpStatus.BAD_REQUEST, "OWNER400", "기존 비밀번호와 동일합니다."),

    //Employee 오류 응답
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE404", "직원이 존재하지 않습니다."),

    //Food 오류 응답
    FOOD_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD404", "음식이 존재하지 않습니다."),

    //FoodCategory 오류 응답
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD_CATEGORY404", "음식 카테고리가 존재하지 않습니다."),
    
    //JWT 오류 응답
    JWT_WRONG_TYPE_TOKEN(HttpStatus.BAD_REQUEST, "JWT400", "JWT 타입이 틀렸습니다."),
    JWT_EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "JWT400", "JWT가 만료되었습니다."),
    JWT_NULL(HttpStatus.UNAUTHORIZED, "JWT401", "JWT가 NULL입니다."),
    JWT_INVALID(HttpStatus.FORBIDDEN, "JWT403", "JWT가 유효하지 않습니다."),

    // File 오류 응답
    FILE_NOT_FOUND_IN_DB(HttpStatus.NOT_FOUND, "FILE_DB404", "파일정보가 DB에 존재하지 않습니다."),
    FILE_NOT_FOUND_IN_STORAGE(HttpStatus.NOT_FOUND, "FILE_STORAGE404", "파일이 존재하지 않습니다."),

    // Payment 오류 응답
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PAYMENT_404", "결제정보가 존재하지 않습니다."),

    // OrderHistory 오류 응답
    ORDERHISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDERHISTORY404", "주문내역이 존재하지 않습니다."),

    ;

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    public Body getBody() {
        return Body.builder()
                .message(message)
                .code(code)
                .isSuccess(httpStatus.is2xxSuccessful())
                .httpStatus(httpStatus)
                .build();
    }

}
