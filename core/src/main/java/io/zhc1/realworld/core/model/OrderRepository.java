package io.zhc1.realworld.core.model;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(int orderId);

    List<Order> findByArticle(Article article);

    List<Order> findByCustomer(User customer);

    List<Order> findByProcessedFalse();

    void delete(Order order);

}
