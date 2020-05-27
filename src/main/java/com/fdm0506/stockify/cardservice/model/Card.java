package com.fdm0506.stockify.cardservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cards")
public class Card {
    @Id String _id;
    String cardOwnerUsername;
    CardType cardType;
    String cardNumber;
    String cardHolderName;
    String expiryDate;
    String cvv;

}
