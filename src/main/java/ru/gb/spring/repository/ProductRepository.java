package ru.gb.spring.repository;

import org.springframework.stereotype.Component;
import ru.gb.spring.model.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Component("productRepository")
public class ProductRepository {

    private List<Product> products;

    @PostConstruct
    private void initRepository() {
        products = List.of(
                new Product(1L, "Red Coffee Mug", new BigDecimal("9.99")),
                new Product(2L, "Black Decker Piano", new BigDecimal("150099.50")),
                new Product(3L, "Orange Hipster Sofa", new BigDecimal("6870.54")),
                new Product(4L, "Rose Diamond Pony", new BigDecimal("4870.09")),
                new Product(5L, "Violet Garden Flower Pot", new BigDecimal("85.62"))
        );
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Long productId) {
        return products.stream()
                .filter( product -> product.getId().equals(productId) )
                .findFirst()
                .orElse(null);
    }

}
