package ru.gb.spring.conrtoller;

import org.springframework.stereotype.Component;
import ru.gb.spring.service.CartService;
import ru.gb.spring.terminal.CommandType;

import java.util.Scanner;

@Component
public class CartController {

    private final CartService cartService;
    private Scanner scanner;
    private boolean isShopping = true;

    public CartController(CartService cartService) {
        this.cartService = cartService;
        this.scanner = new Scanner(System.in);
    }

    public void init() {
        showWelcomeMessage();
        cartService.listProducts();
        showHelp();
        while (isShopping) {
            System.out.print("> ");
            String userMessage = scanner.nextLine().toUpperCase();
            String[] userMessageSplit = userMessage.split("[?\\s]", 0);

            String userCommand = userMessageSplit[0];
            if (! checkUserCommand(userCommand)) {
                System.out.println("Sorry, I didn't get you. Please use only the standard commands (type HELP to show them)");
            } else {
                executeUserCommand(userMessageSplit);
            }
        }
    }

    private void showWelcomeMessage() {
        System.out.println("Welcome to our Online Shop! We offer various products of the highest quality: ");
    }

    private void showHelp() {
        System.out.println("\nPlease type: ");
        System.out.println("\tto show the list of our products: LIST");
        System.out.println("\tto add a product to your shopping cart: ADD [product id] (Ex: ADD 4)");
        System.out.println("\tto remove all products of a certain type from your shopping cart: DEL [product id] (Ex: DEL 4)");
        System.out.println("\tto show the products in your cart: CART");
        System.out.println("\tto stop shopping and checkout: CHECKOUT");
        System.out.println("\tto leave our shop: EXIT");
        System.out.println("\tto show this help message again: HELP");
    }

    private boolean checkUserCommand(String userCommand) {
        for (CommandType commandType: CommandType.values()) {
            if (commandType.toString().equals(userCommand)) {
                return true;
            }
        }
        return false;
    }

    private void executeUserCommand(String[] userMessage) {
        CommandType userCommand = CommandType.valueOf(userMessage[0]);
        switch (userCommand) {
            case HELP:
                showHelp();
                break;
            case LIST:
                cartService.listProducts();
                break;
            case CART:
                cartService.listCartProducts();
                break;
            case ADD:
                if (userMessage.length < 2) {
                    System.out.println("You should provide a valid Product Id");
                } else executeAddCommand(userMessage);
                break;
            case DEL:
                if (userMessage.length < 2) {
                    System.out.println("You should provide a valid Product Id");
                } else executeDeleteCommand(userMessage);
                break;
            case CHECKOUT:
                cartService.checkoutCart();
                System.out.println("Return soon!");
                isShopping = false;
                break;
            case EXIT:
                System.out.println("Return soon!");
                isShopping = false;
                break;
            default:
                System.out.println("OK!");
        }
    }

    private void executeAddCommand(String[] userMessage) {
        try {
            Long productId = Long.valueOf(userMessage[1]);
            cartService.addProductToCart(productId);
        } catch (Exception e) {
            System.out.println("You should provide a valid Product Id");
        }
    }

    private void executeDeleteCommand(String[] userMessage) {
        try {
            Long productId = Long.valueOf(userMessage[1]);
            cartService.deleteProductFromCart(productId);
        } catch (Exception e) {
            System.out.println("You should provide a valid Product Id");
        }
    }

}
