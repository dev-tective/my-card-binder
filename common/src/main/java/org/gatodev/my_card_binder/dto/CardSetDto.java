package org.gatodev.my_card_binder.dto;

import org.gatodev.my_card_binder.enums.TCG;

import java.time.LocalDate;
import java.util.List;

public record CardSetDto(
        Long id,
        TCG tcg,
        String name,
        String code,
        String description,
        List<String> images,
        LocalDate releaseDate
) {
}