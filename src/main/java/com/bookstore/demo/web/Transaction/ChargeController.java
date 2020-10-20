package com.bookstore.demo.web.Transaction;

import com.bookstore.demo.model.Stripe.ChargeRequest;
import com.bookstore.demo.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/stripe")
public class ChargeController {

    private final StripeService stripeService;

    public ChargeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException, AuthenticationException {

        chargeRequest.setDescription("Testing charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = stripeService.charge(chargeRequest);

        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeDescription", charge.getDescription());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        model.addAttribute("error", false);

        return "/stripe-views/result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "/stripe-views/result";
    }
}
