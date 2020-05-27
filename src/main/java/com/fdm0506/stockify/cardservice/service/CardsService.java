package com.fdm0506.stockify.cardservice.service;

import com.fdm0506.stockify.cardservice.model.Card;
import com.fdm0506.stockify.cardservice.repository.CardsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardsService {

    private CardsRepository cardsRepository;
    private EncryptionService cryptoService;

    public CardsService(CardsRepository cardsRepository, EncryptionService cryptoService) {
        this.cardsRepository = cardsRepository;
        this.cryptoService = cryptoService;
    }

    public Flux<Card> getUserCards(String username) {
        return cardsRepository.findAllByCardOwnerUsername(username)
                .map(card -> {
                    card.setCardNumber(cryptoService.decrypt(card.getCardNumber()));
                    return card;
                });
    }

    public Mono<?> saveUserCard(Card card) {
        card.setCardNumber(cryptoService.encrypt(card.getCardNumber()));
        card.setCvv("");
        return cardsRepository.save(card);
    }

    public Mono<?> deleteCard(String _id) {
        return cardsRepository.deleteById(_id);
    }

}
