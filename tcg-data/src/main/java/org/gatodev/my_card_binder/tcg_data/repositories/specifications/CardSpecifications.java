package org.gatodev.my_card_binder.tcg_data.repositories.specifications;

import jakarta.persistence.criteria.*;
import org.gatodev.my_card_binder.filters.CardFilter;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CardSpecifications {

    public static Specification<Card> withFilters(CardFilter filter) {
        return (root, query, cb) -> {
            if (filter == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (filter.tcg() != null) {
                predicates.add(cb.equal(root.get("tcg"), filter.tcg()));
            }

            addLikePredicate(predicates, root.get("name"), filter.name(), cb);
            addLikePredicate(predicates, root.get("code"), filter.code(), cb);
            addLikePredicate(predicates, root.get("rarity"), filter.rarity(), cb);
            addLikePredicate(predicates, root.get("rarityCode"), filter.rarityCode(), cb);
            addLikePredicate(predicates, root.get("type"), filter.type(), cb);
            addLikePredicate(predicates, root.get("archetype"), filter.archetype(), cb);

            // Filtrar por CardSet
            if (filter.setName() != null || filter.setCode() != null) {
                // Hacemos un JOIN a la entidad CardSet.
                // Como es una relación @ManyToOne, usamos root.join
                Join<Card, CardSet> cardSetJoin = root.join("cardSet", JoinType.INNER);

                if (filter.setName() != null) {
                    addLikePredicate(predicates, cardSetJoin.get("name"), filter.setName(), cb);
                }
                if (filter.setCode() != null) {
                    addLikePredicate(predicates, cardSetJoin.get("code"), filter.setCode(), cb);
                }
            }

            // Combina todos los predicados con AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLikePredicate(List<Predicate> predicates,
                                         Expression<String> path,
                                         String value,
                                         CriteriaBuilder cb) {
        if (value != null && !value.isBlank()) {
            predicates.add(cb.like(cb.lower(path), "%" + value.trim().toLowerCase() + "%"));
        }
    }
}