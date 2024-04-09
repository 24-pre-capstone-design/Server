package com.pre_capstone_design_24.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@Tag(name = "orderHistory", description = "주문내역 api")
public class OrderHistoryController {

    

}
