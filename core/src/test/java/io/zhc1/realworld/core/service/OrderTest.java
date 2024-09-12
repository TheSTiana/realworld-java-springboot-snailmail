package io.zhc1.realworld.core.service;

import io.zhc1.realworld.core.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTest {
    @InjectMocks
    OrderService sut;

    @Mock
    OrderRepository orderRepository;

    User author;
    Article article;
    User customer;

    @BeforeEach
    void setUp() {
        author = new TestUser(UUID.randomUUID(), "author", "author", "password");
        article = new TestArticle(1, author, "title", "description", "body");
        customer = new TestUser(UUID.randomUUID(), "customer", "customer", "password");
    }

    @Test
    void whenWriteComment_thenShouldSaveToRepository() {
        // given
        Order order = new Order(article, customer);
        when(orderRepository.save(order)).thenReturn(order);

        // when
        Order result = sut.create(order);

        // then
        assertThat(result).isEqualTo(order);
    }
}
