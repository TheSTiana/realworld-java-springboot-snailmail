package io.zhc1.realworld.core.model;

import jakarta.persistence.*;

public class ArticleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
