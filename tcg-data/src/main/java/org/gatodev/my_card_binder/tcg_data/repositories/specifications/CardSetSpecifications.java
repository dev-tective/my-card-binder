package org.gatodev.my_card_binder.tcg_data.repositories.specifications;

import org.gatodev.my_card_binder.filters.CardSetFilter;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class CardSetSpecifications {

    public static Specification<CardSet> withFilter(CardSetFilter filter) {
        return (root, criteriaQuery, cb) -> {
            if (filter == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (filter.tcg() != null) {
                predicates.add(cb.equal(root.get("tcg"), filter.tcg()));
            }

            if (filter.name() != null && !filter.name().isBlank()) {
                String likePattern = "%" + filter.name().trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), likePattern));
            }

            if (filter.code() != null && !filter.code().isBlank()) {
                String likePattern = "%" + filter.code().trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("code")), likePattern));
            }

            LocalDate fromDate = filter.from();
            LocalDate toDate = filter.to();

            if (fromDate != null) {
                // releaseDate DEBE ser mayor o igual a 'from'
                predicates.add(cb.greaterThanOrEqualTo(root.get("releaseDate"), fromDate));
            }

            if (toDate != null) {
                // releaseDate DEBE ser menor o igual a 'to'
                predicates.add(cb.lessThanOrEqualTo(root.get("releaseDate"), toDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}