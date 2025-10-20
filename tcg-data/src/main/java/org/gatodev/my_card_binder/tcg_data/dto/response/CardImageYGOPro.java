package org.gatodev.my_card_binder.tcg_data.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardImageYGOPro(
        String image_url,
        String image_url_small,
        String image_url_cropped
) {
}
