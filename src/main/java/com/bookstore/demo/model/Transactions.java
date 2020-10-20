package com.bookstore.demo.model;

import com.stripe.model.Charge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    private String id;

    @Column
    private String chargeId;

    @Column
    private String chargeStatus;

    @Column
    private String balanceTransaction;

    @Column
    private String chargeDescription;

    @Column
    private String chargeCurrency;
}
