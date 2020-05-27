package com.fdm0506.stockify.cardservice.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void instantiateCard() {
        //given
        Card card = new Card();
        card.set_id(new ObjectId().toHexString());
        card.setCardOwnerUsername("test");
        card.setCardHolderName("test ch name");
        card.setCvv("777");
        card.setCardType(CardType.MAESTRO);
        card.setExpiryDate("09/23");

        assertEquals("test ch name", card.getCardHolderName());
        assertEquals("test", card.getCardOwnerUsername());
        assertEquals(CardType.MAESTRO, card.getCardType());
        assertEquals("09/23", card.getExpiryDate());
        assertEquals("777", card.getCvv());
    }
}