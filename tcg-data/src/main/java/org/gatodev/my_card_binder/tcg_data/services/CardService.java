package org.gatodev.my_card_binder.tcg_data.services;

import org.gatodev.my_card_binder.filters.CardFilter;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {
    Card addCard(Card card);

    Card updateCard(Card card);

    Card getCardById(Long id);

    void deleteCardById(Long id);

    Page<Card> getAllCardsWithFilter(CardFilter filter, Pageable pageable);
}
