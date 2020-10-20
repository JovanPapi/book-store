package com.bookstore.demo.service.Impl;

import com.bookstore.demo.model.Stripe.ChargeRequest;
import com.bookstore.demo.model.Transactions;
import com.bookstore.demo.repository.TransactionRepository;
import com.bookstore.demo.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StripeServiceImpl implements StripeService {

    private final TransactionRepository transactionRepository;

    public StripeServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");
    }

    @Override
    public Charge charge(ChargeRequest request) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException, com.stripe.exception.AuthenticationException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", request.getAmount());
        chargeParams.put("currency", request.getCurrency());
        chargeParams.put("description", request.getDescription());
        chargeParams.put("source", request.getStripeToken());

        Charge c = Charge.create(chargeParams);
        Transactions t = new Transactions(UUID.randomUUID().toString(), c.getId(), c.getStatus(), c.getBalanceTransaction(),
                c.getDescription(), c.getCurrency());
        transactionRepository.save(t);
        return c;
    }
}
