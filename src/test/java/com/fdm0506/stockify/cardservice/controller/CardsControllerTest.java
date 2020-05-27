package com.fdm0506.stockify.cardservice.controller;

import com.fdm0506.stockify.cardservice.model.Card;
import com.fdm0506.stockify.cardservice.model.CardType;
import com.fdm0506.stockify.cardservice.service.CardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class CardsControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CardsService cardsService;

    @Test
    public void itReturnsValidListOfCardsJson() {
        Card card = new Card();
        card.setCardNumber("111");
        card.setCardOwnerUsername("test");
        card.setCardHolderName("test ch name");
        card.setCvv("777");
        card.setCardType(CardType.MAESTRO);
        card.setExpiryDate("09/23");

        Card card2 = new Card();
        card2.setCardNumber("1111");
        card2.setCardOwnerUsername("test");
        card2.setCardHolderName("test ch1 name");
        card2.setCvv("999");
        card2.setCardType(CardType.VISA);
        card2.setExpiryDate("09/23");

        Flux<Card> cards = Flux.just(card, card2);

        Mockito.when(this.cardsService.getUserCards("test")).thenReturn(cards);

        this.webTestClient.get()
                .uri("/api/v2/cards/user/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].cardNumber").isEqualTo("111")
                .jsonPath("$.[0].cardOwnerUsername").isEqualTo("test")
                .jsonPath("$.[0].cvv").isEqualTo(777)
                .jsonPath("$.[0].cardType").isEqualTo("MAESTRO")
                .jsonPath("$.[1].cardNumber").isEqualTo("1111")
                .jsonPath("$.[1].cardOwnerUsername").isEqualTo("test")
                .jsonPath("$.[1].cvv").isEqualTo(999)
                .jsonPath("$.[1].cardType").isEqualTo("VISA");

    }

    @Test
    public void itCanRemoveACard() {
        Card card = new Card();
        card.setCardNumber("111111111111111");
        card.setCardOwnerUsername("test");
        card.setCardHolderName("test ch name");
        card.setCvv("777");
        card.setCardType(CardType.MAESTRO);
        card.setExpiryDate("09/23");

        Mockito.when(this.cardsService.deleteCard(any()))
                .thenReturn(null);

        this.webTestClient.delete()
                .uri("/api/v2/cards/remove/111111111111111")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void itCanSaveANewCard() {
        Card card = new Card();
        card.setCardNumber("111111111111111");
        card.setCardOwnerUsername("test");
        card.setCardHolderName("test ch name");
        card.setCvv("777");
        card.setCardType(CardType.MAESTRO);
        card.setExpiryDate("09/23");

        Mockito.when(this.cardsService.saveUserCard(any()))
                .thenReturn(null);

        this.webTestClient.post()
                .uri("/api/v2/cards/new")
                .body(BodyInserters.fromValue(card))
                .exchange()
                .expectStatus().isOk();
    }
}