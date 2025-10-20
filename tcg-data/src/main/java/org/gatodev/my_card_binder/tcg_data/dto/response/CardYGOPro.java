package org.gatodev.my_card_binder.tcg_data.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardYGOPro(
        String name,
        String humanReadableCardType,
        String desc,
        String archetype,
        String ygoprodeck_url,
        List<CardSetYGOPro> card_sets,
        List<CardImageYGOPro> card_images
) {
}

