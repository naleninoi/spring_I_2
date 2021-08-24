package ru.gb.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.gb.spring.contoller.CartController;
import ru.gb.spring.service.CartService;

public class MainApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_annotation_config.xml");

        CartController cartController = context.getBean(CartController.class);
        cartController.init();

    }

}
