package org.gatodev.my_card_binder.filters;

import org.gatodev.my_card_binder.enums.TCG;

import java.time.LocalDate;

public record CardSetFilter(
        TCG tcg,
        String name,
        String code,
        LocalDate from,
        LocalDate to
) {
}