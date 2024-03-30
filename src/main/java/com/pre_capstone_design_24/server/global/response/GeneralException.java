package com.pre_capstone_design_24.server.global.response;

public class GeneralException extends RuntimeException {

    private Status status;

    public Body getBody() {
        return this.status.getBody();
    }

}
