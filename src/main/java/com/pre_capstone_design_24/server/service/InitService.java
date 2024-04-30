package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.FoodStatus;
import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import com.pre_capstone_design_24.server.domain.UploadedFile;
import com.pre_capstone_design_24.server.global.util.ImageConverter;
import com.pre_capstone_design_24.server.requestDto.FoodRequestDto;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import com.pre_capstone_design_24.server.requestDto.OrderRequestDto;
import com.pre_capstone_design_24.server.responseDto.UploadedFileResponseDto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class InitService {

    private final FoodCategoryService foodCategoryService;

    private final FileService fileService;

    private final FoodService foodService;

    private final PaymentService paymentService;

    private final OrderHistoryService orderHistoryService;

    private final OrderService orderService;

    public void addCategories() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/foodCategory.txt");
        File foodCategories = resource.getFile();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new BufferedReader(new FileReader(foodCategories)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] token = line.split(" ");
            String foodCategoryName = token[0];
            foodCategoryService.createFoodCategory(foodCategoryName);
        }
    }

    public void addFoods() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/food.txt");
        File foods = resource.getFile();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new BufferedReader(new FileReader(foods)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] token = line.split(" ");

            Long foodCategoryId = Long.parseLong(token[0]);
            String foodName = token[1];
            int foodPrice = Integer.parseInt(token[2]);
            FoodStatus foodStatus = FoodStatus.valueOf(token[3]);
            ClassPathResource imageResource = new ClassPathResource("static/" + foodName + ".jpeg");
            File imageFile = imageResource.getFile();

            String url = addImages(imageFile);

            FoodRequestDto foodRequestDto = FoodRequestDto
                    .builder()
                    .foodCategoryId(foodCategoryId)
                    .name(foodName)
                    .price(foodPrice)
                    .status(foodStatus)
                    .pictureURL(url)
                    .build();

            foodService.createFood(foodRequestDto);
        }
    }

    public String addImages(File file) throws IOException {
        MultipartFile multipartFile = ImageConverter.convertToMultipartFile(file);
        UploadedFileResponseDto uploadedFileResponseDto = fileService.saveFile(multipartFile);
        return uploadedFileResponseDto.getUrl();
    }

    public void addOrderHistories() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/orderHistory.txt");
        File foods = resource.getFile();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new BufferedReader(new FileReader(foods)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] token = line.split(" ");
            int length = token.length;
            Long paymentId = paymentService.createPayment();
            List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                Long foodId = Long.parseLong(token[i]);
                i++;
                int quantity = Integer.parseInt(token[i]);
                OrderRequestDto orderRequestDto
                        = OrderRequestDto.builder().
                        foodId(foodId)
                        .quantity(quantity)
                        .build();
                orderRequestDtoList.add(orderRequestDto);
            }

            OrderHistoryRequestDto orderHistoryRequestDto
                    = OrderHistoryRequestDto.builder()
                    .paymentId(paymentId)
                    .orderRequestDtoList(orderRequestDtoList)
                    .build();

            orderHistoryService.createOrderHistory(orderHistoryRequestDto);
        }

    }

}
