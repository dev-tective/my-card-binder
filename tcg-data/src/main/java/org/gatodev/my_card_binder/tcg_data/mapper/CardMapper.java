package org.gatodev.my_card_binder.tcg_data.mapper;

import org.gatodev.my_card_binder.dto.CardDto;
import org.gatodev.my_card_binder.dto.CardSummaryDto;
import org.mapstruct.Mapper;
import org.gatodev.my_card_binder.tcg_data.entities.Card;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(Card card);

    CardSummaryDto toSummaryDto(Card card);
}