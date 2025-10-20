package org.gatodev.my_card_binder.tcg_data.services;

import lombok.extern.slf4j.Slf4j;
import org.gatodev.my_card_binder.filters.CardSetFilter;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.gatodev.my_card_binder.tcg_data.exceptions.not_found.CardSetNotFoundException;
import org.gatodev.my_card_binder.tcg_data.repositories.CardSetRepository;
import org.gatodev.my_card_binder.tcg_data.repositories.specifications.CardSetSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CardSetServiceImpl implements CardSetService {
    private final CardSetRepository repository;

    public CardSetServiceImpl(CardSetRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public CardSet addCardSet(CardSet cardSet) {
        return repository.save(cardSet);
    }

    @Override
    public CardSet updateCardSet(CardSet cardSet) {
        if (cardSet.getId() == null) {
            throw new IllegalArgumentException("Card Set ID cannot be null for update operation.");
        }

        if (!repository.existsById(cardSet.getId())) {
            throw new CardSetNotFoundException(cardSet.getId());
        }

        return repository.save(cardSet);
    }

    @Transactional(readOnly = true)
    @Override
    public CardSet getCardSetById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException(id));
    }

    @Override
    public void deleteCardSetById(Long id) {
        if (!repository.existsById(id)) {
            throw new CardSetNotFoundException(id);
        }

        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CardSet> getAllCardSetsByFilter(CardSetFilter filter, Pageable pageable) {
        return repository.findAll(CardSetSpecifications.withFilter(filter), pageable);
    }
}
