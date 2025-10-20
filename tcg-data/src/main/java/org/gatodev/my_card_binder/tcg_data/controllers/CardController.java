package org.gatodev.my_card_binder.tcg_data.controllers;

import org.gatodev.my_card_binder.dto.CardDto;
import org.gatodev.my_card_binder.dto.CardSummaryDto;
import org.gatodev.my_card_binder.filters.CardFilter;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.mapper.CardMapper;
import org.gatodev.my_card_binder.tcg_data.services.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;
    private final CardMapper cardMapper;

    public CardController(CardService cardService, CardMapper cardMapper) {
        this.cardService = cardService;
        this.cardMapper = cardMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Long id) {
        return ResponseEntity.ok(cardMapper.toDto(cardService.getCardById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<CardSummaryDto>> getAllCards(@ModelAttribute CardFilter filter, Pageable pageable) {
        Page<Card> cardPage = cardService.getAllCardsWithFilter(filter, pageable);
        return ResponseEntity.ok(cardPage.map(cardMapper::toSummaryDto));
    }
}
