package org.gatodev.my_card_binder.tcg_data.exceptions.not_found;

public class CardNotFoundException extends TCGDataEntityNotFoundException {
    public CardNotFoundException(Long id) {
        super("Card", id);
    }
}
