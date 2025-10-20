package org.gatodev.my_card_binder.tcg_data.repositories;

import org.gatodev.my_card_binder.enums.TCG;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, Long>, JpaSpecificationExecutor<CardSet> {
    boolean existsByCode(String code);

    boolean existsByTcgAndNameAndCode(TCG tcg, String name, String code);
}
