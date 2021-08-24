package ru.gb.spring.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("cart")
@Scope("prototype")
public class Cart {

    private HashMap<Product, Integer> products = new HashMap<>();

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        if ( !products.containsKey(product) ) {
            products.put(product, 1);
        } else {
            Integer quantity =  products.get(product);
            products.put(product, ++ quantity);
        }
    }

    public Integer removeProduct(Product product) {
        return products.remove(product);
    }


}
