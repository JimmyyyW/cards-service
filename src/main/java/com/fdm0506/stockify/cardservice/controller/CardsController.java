package com.fdm0506.stockify.cardservice.controller;

import com.fdm0506.stockify.cardservice.model.Card;
import com.fdm0506.stockify.cardservice.service.CardsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CardsController {

    private CardsService cardsService;

    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping("/api/v2/cards/user/{username}")
    public Flux<Card> getUserCards(@PathVariable String username) {
        return cardsService.getUserCards(username);
    }

    @PostMapping("/api/v2/cards/new")
    public Mono<?> saveCard(@RequestBody Card card) {
        return cardsService.saveUserCard(card);
    }

    @CrossOrigin
    @DeleteMapping("/api/v2/cards/remove/{_id}")
    public Mono<?> deleteCard(@PathVariable String _id) {
        return cardsService.deleteCard(_id);
    }

}
