package org.gatodev.my_card_binder.dto;

import org.gatodev.my_card_binder.enums.TCG;

import java.util.List;

public record CardSummaryDto(
        Long id,
        String name,
        String code,
        String rarity,
        String rarityCode,
        TCG tcg,
        String type,
        List<String> subtypes,
        String image,
        long wanted,
        CardSetSummaryDto cardSet
) {
}
