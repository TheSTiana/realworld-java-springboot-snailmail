package io.zhc1.realworld.persistence;
import io.zhc1.realworld.core.model.Article;
import io.zhc1.realworld.core.model.Order;
import io.zhc1.realworld.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface OrderJpaRepository extends JpaRepository<Order, Integer> {

    List<Order> findByArticleOrderByCreatedAtDesc(Article article);

    List<Order> findByCustomer(User customer);

}