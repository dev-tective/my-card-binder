package org.gatodev.my_card_binder.filters;

import org.gatodev.my_card_binder.enums.TCG;

import java.util.List;

public record CardFilter(
        TCG tcg,
        String name,
        String code,
        String rarity,
        String rarityCode,
        String type,
        List<String> subtypes,
        String archetype,
        String setName,
        String setCode
) {
}
