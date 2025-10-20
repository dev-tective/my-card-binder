package org.gatodev.my_card_binder.tcg_data.services;

import org.gatodev.my_card_binder.filters.CardSetFilter;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardSetService {
    CardSet addCardSet(CardSet cardSet);

    CardSet updateCardSet(CardSet cardSet);

    CardSet getCardSetById(Long id);

    void deleteCardSetById(Long id);

    Page<CardSet> getAllCardSetsByFilter(CardSetFilter filter, Pageable pageable);
}
