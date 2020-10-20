package com.bookstore.demo.service;

import com.bookstore.demo.model.Stripe.ChargeRequest;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import javax.naming.AuthenticationException;

public interface StripeService {
    Charge charge(ChargeRequest request) throws AuthenticationException,
            com.stripe.exception.AuthenticationException,
            InvalidRequestException, APIConnectionException,
            CardException, APIException;
}
