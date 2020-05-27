package com.fdm0506.stockify.cardservice.service;

import com.fdm0506.stockify.cardservice.model.Card;
import com.fdm0506.stockify.cardservice.model.CardType;
import com.fdm0506.stockify.cardservice.repository.CardsRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class CardsServiceTest {

    private CardsRepository cardsRepository = mock(CardsRepository.class);
    private EncryptionService cryptoService = mock(EncryptionService.class);
    private CardsService subject = new CardsService(cardsRepository, cryptoService);

    @Test
    public void itCanSaveANewCardAndReturnFromMongo() {
        Card card = new Card(
                new ObjectId().toHexString(),
                "test",
                CardType.MAESTRO,
                "1111999911119999",
                "test ch name",
                "09/23",
                "888"
        );

        when(subject.saveUserCard(card)).thenReturn(Mono.empty());

        Mono<?> actual = subject.saveUserCard(card);

        verify(cardsRepository, times(1)).save(any());
        assertEquals(actual.block(), Mono.empty().block());
    }

    @Test
    public void itCanReturnMultipleUserCards() {
        Card card = new Card(
                new ObjectId().toHexString(),
                "test",
                CardType.MAESTRO,
                "1111999911119999",
                "test ch name",
                "09/23",
                "888"
        );
        Card card2 = new Card(
                new ObjectId().toHexString(),
                "test",
                CardType.MAESTRO,
                "1111999911119999",
                "test ch name",
                "09/23",
                "888"
        );


        Flux<Card> source = Flux.just(card, card2);

        when(cardsRepository.findAllByCardOwnerUsername(any()))
                .thenReturn(source);

        when(cryptoService.decrypt(any())).thenReturn("1111999911119999");

        Flux<Card> actual = subject.getUserCards("test");

        verify(cardsRepository, times(1)).findAllByCardOwnerUsername("test");

        assertEquals(actual.blockFirst(), card);
        assertEquals(actual.blockLast(), card2);

    }

    @Test
    public void itCanDeleteCard() {
        Card card = new Card();
        card.set_id(new ObjectId().toHexString());
        card.setCardNumber("111");
        card.setCardOwnerUsername("test");
        card.setCardHolderName("test ch name");
        card.setCvv("777");
        card.setCardType(CardType.MAESTRO);
        card.setExpiryDate("09/23");


        when(subject.deleteCard(any())).thenReturn(Mono.empty());

        Mono<?> actual = subject.deleteCard("111");

        verify(cardsRepository, times(1)).deleteByCardNumber(any());
        assertEquals(actual.block(), Mono.empty().block());
    }

}