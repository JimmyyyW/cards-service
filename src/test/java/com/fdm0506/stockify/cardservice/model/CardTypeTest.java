package com.fdm0506.stockify.cardservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTypeTest {

    @Test
    public void testValidValues() {
        CardType type = CardType.MAESTRO;
        CardType type2 = CardType.VISA;
        CardType type3 = CardType.MASTERCARD;
        CardType type4 = CardType.UNKNOWN;

        assertEquals(CardType.MAESTRO, type);
        assertEquals(CardType.VISA, type2);
        assertEquals(CardType.MASTERCARD, type3);
        assertEquals(CardType.UNKNOWN, type4);
    }

}