package com.fdm0506.stockify.cardservice.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    EncryptionService encryptionService = new EncryptionService();

    @Test
    public void encryptionSuccessfullyEncryptsString() {
        String encrypted = encryptionService.encrypt("hello");

        assertEquals("VX8ziUWt1sbTlk2YuDq++w==", encrypted);
    }

    @Test
    public void decryptionOfEncryptedDataSuccessful() {
        String decrypted = encryptionService.decrypt("VX8ziUWt1sbTlk2YuDq++w==");

        assertEquals("hello", decrypted);
    }

    @Test
    public void itCanEncryptAndDecryptSingleCardNumber() {
        //given
        String cardNumber = "9999444433331111";

        //when
        String encrypted = encryptionService.encrypt(cardNumber);

        String decrypted = encryptionService.decrypt(encrypted);

        //then
        assertEquals(cardNumber, decrypted);

    }

}