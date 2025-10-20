package org.gatodev.my_card_binder.tcg_data.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.gatodev.my_card_binder.enums.TCG;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.gatodev.my_card_binder.tcg_data.repositories.CardRepository;
import org.gatodev.my_card_binder.tcg_data.repositories.CardSetRepository;
import org.gatodev.my_card_binder.tcg_data.services.fetch.context.TCGFetcherContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EntityUpdater {
    private final CardSetRepository cardSetRepo;
    private final CardRepository cardRepo;
    private final TCGFetcherContext context;

    public EntityUpdater(CardSetRepository cardSetRepository,
                         CardRepository cardRepository,
                         TCGFetcherContext context) {
        this.cardSetRepo = cardSetRepository;
        this.cardRepo = cardRepository;
        this.context = context;
    }

    @PostConstruct
    public void init() {
        List<TCG> existingTcgs = cardRepo.findDistinctTcgs();
        TCG[] missingTcgs = context.getTcgStrategies()
                .stream()
                .filter(tcg -> !existingTcgs.contains(tcg))
                .toArray(TCG[]::new);

        if (missingTcgs.length > 0) {
            log.info("Starting set loading for missing TCGs: {}", Arrays.toString(missingTcgs));

            List<Card> cards = context.getCards(missingTcgs);

            context.getCardSets(missingTcgs)
                    .forEach(cs -> updateCardSetWithCards(cs, cards));
        } else {
            log.info("The database already contains sets for all defined TCGs. Initialization not required.");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void periodicUpdate() {
        context.getTcgStrategies().forEach(this::updateByTcg);
    }

    public void updateByTcg(TCG tcg) {
        List<CardSet> cardSets = context.getCardSets(tcg)
                .stream()
                .filter(cs -> !cardSetRepo.existsByCode(cs.getCode()))
                .toList();

        cardSets.forEach(cs -> {
            List<Card> cards = context.getCardsBySet(cs.getTcg(), cs);
            updateCardSetWithCards(cs, cards);
        });
    }

    public void updateCardSetWithCards(CardSet cardSet, List<Card> totalCards) {
        if (cardSetRepo.existsByTcgAndNameAndCode(cardSet.getTcg(),  cardSet.getName(), cardSet.getCode())) {
            return;
        }

        List<Card> cards = totalCards.stream()
                .filter(c -> c.getTcg().equals(cardSet.getTcg()) &&
                        c.getCode().contains(cardSet.getCode()))
                .toList();

        cardSetRepo.save(cardSet);
        cards.forEach(card -> card.setCardSet(cardSet));
        cardRepo.saveAll(cards);

        String cardsInfo = cards.stream()
                .map(c -> String.format("%s %s (%s)", c.getName(), c.getCode(), c.getRarity()))
                .collect(Collectors.joining(", "));

        log.info("Card Set {} for TCG {} has been saved", cardSet.getName(), cardSet.getTcg());
        log.info("Populating Card Set {} with {} cards: [{}]", cardSet.getName(), cards.size(), cardsInfo);
    }
}
