package io.zhc1.realworld.core.background;

import io.zhc1.realworld.core.model.*;
import io.zhc1.realworld.core.service.ArticleService;
import io.zhc1.realworld.core.service.OrderService;
import io.zhc1.realworld.core.service.UserService;
import net.datafaker.Faker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ProcessOrders {
    private final OrderService orderService;
    private final UserService userService;
    private final ArticleService articleService;
    private final Faker faker;

    public ProcessOrders(OrderService orderService, UserService userService, ArticleService articleService) {
        this.orderService = orderService;
        this.userService = userService;
        this.articleService = articleService;

        this.faker = new Faker();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void process() {
        //createOrders(faker);

        System.out.println("FINDING UNPROCESSED ORDERS");
        List<Order> orders = orderService.getUnprocessedOrders();
        System.out.println("FOUND " + orders.size() + " ORDERS");

        for (Order order : orders) {

            System.out.println(order);

            if(order.isToManyErrors()){
                System.out.println("THIS ORDER SUCKS");
                continue;
            }

            if(order.isProblemsWithSnailMailAddress()){
                System.out.println("SHITTY SNAIL MAIL ADDRESS");
                continue;
            }

            int num = faker.number().numberBetween(0, 100);

            if(num < 20){
                order.increaseErrors();
                orderService.update(order);
                System.out.println("THE SUPPLIER SHOULD BE FIRED");
                continue;
            }

            order.setProcessed(true);
            orderService.update(order);
            System.out.println("ORDER HAS BEEN GHOSTED");
        }
    }

    private void createOrders(Faker faker){
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            articles.add(createFakeArticle(faker));
        }

        for (Article article : articles) {
            for (int i = 0; i < 2; i++) {
                createFakeOrder(faker, article);
            }
        }
    }

    private User createFakeUser(Faker faker){
        String username = faker.name().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        while(username.length() > 30 || email.length() > 30 || password.length() > 200){
            username = faker.name().username();
            email = faker.internet().emailAddress();
            password = faker.internet().password();
        }
        return userService.signup(new UserRegistry(email, username, password));
    }

    private Article createFakeArticle(Faker faker){
        boolean retry = true;
        double digitalPrice = faker.number().numberBetween(10, 90);
        String title;
        String description;
        String content;
        boolean ex;

        do{
            title = faker.book().title();
            description = faker.lorem().sentence();
            content = faker.lorem().paragraph();

            try{
                articleService.getArticle(titleToSlug(title));
                ex = false;
            }
            catch (NoSuchElementException e){
                ex = true;
            }
            retry = title.length() > 50 || description.length() > 50 || content.length() > 1000 || ex;
        }
        while (retry);

        Article article = new Article(createFakeUser(faker), title, description, content, digitalPrice, digitalPrice + 15);
        Collection<Tag> tags = new ArrayList<>();
        return articleService.write(article, tags);
    }

    public Order createFakeOrder(Faker faker, Article article){
        User customer = createFakeUser(faker);
        boolean digital = faker.bool().bool();

        if(digital){
            return orderService.create(new Order(article, customer));
        }
        String snailMail = faker.address().fullAddress();
        if(snailMail.length() > 50){
            Order order = new Order(article, customer, "Unknown");
            order.setProblemsWithSnailMailAddress(true);
            return orderService.create(order);
        }
        return orderService.create(new Order(article, customer, snailMail));
    }

    private static String titleToSlug(String title) {
        return title.toLowerCase().replaceAll("\\s+", "-");
    }

}
