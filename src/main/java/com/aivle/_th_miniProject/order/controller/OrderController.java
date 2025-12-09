package com.aivle._th_miniProject.order.controller;

import com.aivle._th_miniProject.order.dto.OrderCreateRequest;
import com.aivle._th_miniProject.order.dto.OrderResponse;
import com.aivle._th_miniProject.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    // 결제 처리
    @PostMapping("/{orderId}/pay")
    public OrderResponse payOrder(@PathVariable Long orderId) {
        return orderService.payOrder(orderId);
    }

    // 주문 취소
    @PostMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

}
