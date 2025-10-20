package org.gatodev.my_card_binder.tcg_data.mapper;

import org.gatodev.my_card_binder.dto.CardSetDto;
import org.gatodev.my_card_binder.dto.CardSetSummaryDto;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardSetMapper {

    CardSetDto toDto(CardSet cardSet);

    CardSetSummaryDto toSummaryDto(CardSet cardSet);
}