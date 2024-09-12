package io.zhc1.realworld.core.service;

import io.zhc1.realworld.core.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order getOrder(int id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("invalid comment id."));
    }

    public List<Order> getOrders(Article article) {
        return orderRepository.findByArticle(article);
    }

    public List<Order> getOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

}
