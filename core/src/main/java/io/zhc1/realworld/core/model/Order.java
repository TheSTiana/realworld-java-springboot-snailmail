package io.zhc1.realworld.core.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(length = 50, nullable = true)
    private String snailMail;

    @Column(nullable = false)
    private double totalPrice;

    public Order(Article article, User customer, String snailMail) {
        if (article == null || article.getId() == null) {
            throw new IllegalArgumentException("article is null or not saved article.");
        }
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("author is null or unknown user.");
        }
        if(snailMail == null || snailMail.isBlank())
            throw new IllegalArgumentException("snailmail must not be null or blank when wanting physical.");

        this.article = article;
        this.customer = customer;
        this.snailMail = snailMail;
        this.totalPrice = article.getDigitalPrice() + article.getPhysicalPrice();
    }

    public Order(Article article, User customer) {
        if (article == null || article.getId() == null) {
            throw new IllegalArgumentException("article is null or not saved article.");
        }
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("author is null or unknown user.");
        }
        this.article = article;
        this.customer = customer;
        this.snailMail = null;
        this.totalPrice = article.getDigitalPrice();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Order other && Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
