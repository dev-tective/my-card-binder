package org.gatodev.my_card_binder.tcg_data.services;

import lombok.extern.slf4j.Slf4j;
import org.gatodev.my_card_binder.filters.CardFilter;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.exceptions.not_found.CardNotFoundException;
import org.gatodev.my_card_binder.tcg_data.repositories.CardRepository;
import org.gatodev.my_card_binder.tcg_data.repositories.specifications.CardSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CardServiceImpl implements CardService {
    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Card addCard(Card card) {
        return repository.save(card);
    }

    @Transactional
    @Override
    public Card updateCard(Card card) {
        if (card.getId() == null) {
            throw new IllegalArgumentException("Card ID cannot be null for update operation.");
        }

        if (!repository.existsById(card.getId())) {
            throw new CardNotFoundException(card.getId());
        }

        return repository.save(card);
    }

    @Transactional
    @Override
    public Card getCardById(Long id) {
        Card card = repository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        card.setWanted(card.getWanted() + 1);
        return card;
    }

    @Transactional
    @Override
    public void deleteCardById(Long id) {
        if (!repository.existsById(id)) {
            throw new CardNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Card> getAllCardsWithFilter(CardFilter filter, Pageable pageable) {
        return repository.findAll(CardSpecifications.withFilters(filter), pageable);
    }
}
