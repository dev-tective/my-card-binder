package org.gatodev.my_card_binder.tcg_data.services.fetch.context;

import lombok.extern.slf4j.Slf4j;
import org.gatodev.my_card_binder.enums.TCG;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.gatodev.my_card_binder.tcg_data.services.fetch.TCGFetcherStrategy;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TCGFetcherContext {
    private final Map<TCG, TCGFetcherStrategy> strategies;

    public TCGFetcherContext(List<TCGFetcherStrategy> fetchers) {
        this.strategies = new HashMap<>();
        fetchers.forEach(fetcher ->
                strategies.put(fetcher.getTCG(), fetcher));
    }

    public List<TCG> getTcgStrategies() {
        return List.of(strategies.keySet().toArray(new TCG[0]));
    }

    public List<Card>  getCards(TCG... tcgs) {
        return Arrays.stream(tcgs)
                .map(strategies::get)
                .filter(Objects::nonNull)
                .flatMap(fetcher -> fetcher
                        .fetchCards().stream())
                .toList();
    }

    public List<Card> getCardsBySet(TCG tcg, CardSet cardSet) {
        TCGFetcherStrategy strategy = strategies.get(tcg);

        if (strategy == null) {
            log.warn("Cannot fetch cards for TCG {}: No strategy implemented.", tcg);
            return List.of();
        }

        return strategy.fetchCardsBySet(cardSet);
    }

    public List<CardSet> getCardSets(TCG... tcgs) {
        return Arrays.stream(tcgs)
                .map(strategies::get)
                .filter(Objects::nonNull)
                .flatMap(fetcher -> fetcher
                        .fetchCardSets().stream())
                .toList();
    }
}
