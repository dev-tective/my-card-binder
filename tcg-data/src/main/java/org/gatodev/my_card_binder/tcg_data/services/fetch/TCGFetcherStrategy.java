package org.gatodev.my_card_binder.tcg_data.services.fetch;

import org.gatodev.my_card_binder.enums.TCG;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;

import java.util.List;

public interface TCGFetcherStrategy {
    List<Card> fetchCards();

    List<Card> fetchCardsBySet(CardSet cardSet);

    List<CardSet>  fetchCardSets();

    TCG getTCG();
}
