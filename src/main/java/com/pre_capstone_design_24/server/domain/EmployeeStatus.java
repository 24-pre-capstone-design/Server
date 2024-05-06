package com.pre_capstone_design_24.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeeStatus {
    WORKING("WORKING", "Indicates currently working"),
    OFF_WORK("OFF_WORK", "Indicates that you are currently off work, that is, not working"),
    VACATION("VACATION", "Indicates work days but not working"),
    ASSISTANT_MANAGER_ATTENDANCE("ASSISTANT_MANAGER_ATTENDANCE", "It is not a work date, but indicates when you showed up to work");

    private final String key;
    private final String description;
}
