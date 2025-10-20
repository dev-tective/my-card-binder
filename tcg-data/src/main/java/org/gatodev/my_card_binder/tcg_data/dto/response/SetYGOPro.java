package org.gatodev.my_card_binder.tcg_data.dto.response;

import java.time.LocalDate;

public record SetYGOPro(
        String set_name,
        String set_code,
        Integer num_of_cards,
        LocalDate tcg_date,
        String set_image
) {
}
