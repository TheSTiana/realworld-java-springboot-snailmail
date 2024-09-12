package io.zhc1.realworld.persistence;
import io.zhc1.realworld.core.model.Article;
import io.zhc1.realworld.core.model.Order;
import io.zhc1.realworld.core.model.OrderRepository;
import io.zhc1.realworld.core.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return orderJpaRepository.findById(id);
    }

    @Override
    public List<Order> findByArticle(Article article) {
        return orderJpaRepository.findByArticleOrderByCreatedAtDesc(article);
    }

    @Override
    public List<Order> findByCustomer(User customer) {
        return orderJpaRepository.findByCustomer(customer);
    }

    @Override
    @Transactional
    public void delete(Order order) {
        orderJpaRepository.delete(order);
    }

}

