package com.aivle._th_miniProject.order.service;

import com.aivle._th_miniProject.book.entity.Book;
import com.aivle._th_miniProject.book.repository.BookRepository;
import com.aivle._th_miniProject.order.dto.OrderCreateRequest;
import com.aivle._th_miniProject.order.dto.OrderResponse;
import com.aivle._th_miniProject.order.entity.Order;
import com.aivle._th_miniProject.order.entity.OrderItem;
import com.aivle._th_miniProject.order.repository.OrderRepository;
import com.aivle._th_miniProject.user.User;
import com.aivle._th_miniProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public OrderResponse createOrder(OrderCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Order order = Order.builder()
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .build();


        for (OrderCreateRequest.OrderItemRequest itemRequest : request.getItems()) {

            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("도서 없음"));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .book(book)
                    .quantity(itemRequest.getQuantity())
//                    .price(book.getPrice())
                    .build();

            order.getItems().add(orderItem);

//            total += book.getPrice() * itemRequest.getQuantity();
        }

//        order.setTotalAmount(total);

        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    public OrderResponse payOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));

        if (order.getStatus() == Order.OrderStatus.CANCELLED)
            throw new IllegalStateException("취소된 주문입니다.");

        order.setStatus(Order.OrderStatus.PAID);
        orderRepository.save(order);

        return OrderResponse.from(order);
    }

    public OrderResponse cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));

        if (order.getStatus() == Order.OrderStatus.PAID)
            throw new IllegalStateException("이미 결제된 주문은 취소 불가");

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);

        return OrderResponse.from(order);
    }

    public OrderResponse getOrderDetail(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(OrderResponse::from).toList();
    }

}
