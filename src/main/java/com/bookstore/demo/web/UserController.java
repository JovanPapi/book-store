package com.bookstore.demo.web;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.ShoppingCart;
import com.bookstore.demo.model.Stripe.ChargeRequest;
import com.bookstore.demo.model.User;
import com.bookstore.demo.service.Impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final UserServiceImpl userService;
    private String stripePublicKey = System.getenv("STRIPE_PUBLIC_KEY");

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/my-cart")
    public String myCart(Model model) {
        User user = userService.fetchAllUsers().get(0);
        ShoppingCart cart = user.getShoppingCarts().get(0);
        List<Book> cartBooks = cart.getBooks();
        model.addAttribute("userCart", cart);
        model.addAttribute("books", cartBooks);
        model.addAttribute("user", user);
        int finalPrice = 0;
        for (Book b : cartBooks) {
            finalPrice += b.getPrice();
        }
        model.addAttribute("finalAmount", finalPrice);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "user-cart";
    }

    @PostMapping(value = "/delete-cart/{id}")
    public String deleteCart(@PathVariable String id) {
        userService.deleteCart(id);
        return "redirect:/books";
    }

    @PostMapping(value = "/buy-cart/{id}")
    public String buyCart(@PathVariable String id) {
        if (!userService.userBuyCart(id)) {
            return "redirect:/user/my-cart" + "?error=One or more books have copies below 0 and they cant be bought!";
        }
        return "redirect:/books";
    }
}
