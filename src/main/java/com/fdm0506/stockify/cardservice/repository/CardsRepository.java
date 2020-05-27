package com.fdm0506.stockify.cardservice.repository;

import com.fdm0506.stockify.cardservice.model.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardsRepository extends ReactiveMongoRepository<Card, String> {

    Flux<Card> findAllByCardOwnerUsername(String username);

    Mono<?> deleteByCardNumber(String cardNumber);
}
