package org.gatodev.my_card_binder.tcg_data.controllers;

import org.gatodev.my_card_binder.dto.CardSetDto;
import org.gatodev.my_card_binder.filters.CardSetFilter;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.gatodev.my_card_binder.tcg_data.mapper.CardSetMapper;
import org.gatodev.my_card_binder.tcg_data.services.CardSetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cardset")
public class CardSetController {
    private final CardSetService cardSetService;
    private final CardSetMapper cardSetMapper;

    public CardSetController(CardSetService cardSetService, CardSetMapper cardSetMapper) {
        this.cardSetService = cardSetService;
        this.cardSetMapper = cardSetMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardSetDto> getCardSetById(@PathVariable Long id) {
        return ResponseEntity.ok(cardSetMapper.toDto(cardSetService.getCardSetById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<CardSetDto>> getAllCardSets(@ModelAttribute CardSetFilter filter, Pageable page) {
        Page<CardSet> cardSets = cardSetService.getAllCardSetsByFilter(filter, page);
        return ResponseEntity.ok(cardSets.map(cardSetMapper::toDto));
    }
}
