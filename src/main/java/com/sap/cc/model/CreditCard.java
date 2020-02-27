package com.sap.cc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

@Entity
@Table(name = "CREDIT_CARD")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    private String name;
    private String cardNumber;
    private Double cardLimit;

    @Builder.Default
    private Double balance = 0.0;
}
