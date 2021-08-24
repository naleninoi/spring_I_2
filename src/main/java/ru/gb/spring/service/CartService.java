package ru.gb.spring.service;

import org.springframework.stereotype.Component;
import ru.gb.spring.model.Cart;
import ru.gb.spring.model.Product;
import ru.gb.spring.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CartService {

    private final ProductRepository productRepository;
    private final Cart cart;

    public CartService(ProductRepository productRepository, Cart cart) {
        this.productRepository = productRepository;
        this.cart = cart;
    }

    public void listProducts() {
        productRepository.getAllProducts()
                .forEach(System.out::println);
    }

    public int countCartProducts() {
        return cart.getProducts().values()
                .stream()
                .reduce(0, Integer::sum);
    }

    public BigDecimal countCartTotalSum() {
        BigDecimal totalSum = new BigDecimal("0");
        for (Map.Entry<Product, Integer> position : cart.getProducts().entrySet()) {
            BigDecimal productPrice = position.getKey().getPrice();
            Integer productQuantity = position.getValue();
            BigDecimal positionSum = productPrice.multiply(BigDecimal.valueOf(productQuantity));
            totalSum = totalSum.add(positionSum);
        }
        return totalSum;
    }

    public void listCartProducts() {
        cart.getProducts().forEach( (product, quantity) ->
                System.out.println(product + ": " + quantity + " pcs"));
        if (cart.getProducts().size() == 0) {
            System.out.println("Your cart is empty");
        }
        System.out.printf("Total cost: %.2f $\n", countCartTotalSum());
    }

    public void checkoutCart() {
        int quantity = countCartProducts();
        BigDecimal sum = countCartTotalSum();
        System.out.printf("Your order has %d products for %.2f $ total\n", quantity, sum);
    }

    public void addProductToCart(Long productId) {
        Product product = productRepository.getProductById(productId);
        if ( product != null ) {
            cart.addProduct(product);
            System.out.printf("%s added to your cart\n", product.getName());
        } else {
            System.out.printf("Product with Id%d not found\n", productId);
        }
    }

    public void deleteProductFromCart(Long productId) {
        Product product = productRepository.getProductById(productId);
        if (product == null) {
            System.out.printf("Product with Id%d not found\n", productId);
        } else {
            Integer result = cart.removeProduct(product);
            if (result != null) {
                System.out.printf("%s successfully deleted from your cart\n", product.getName());
            } else {
                System.out.printf("%s not found in your cart\n", product.getName());
            }
        }
    }

}
