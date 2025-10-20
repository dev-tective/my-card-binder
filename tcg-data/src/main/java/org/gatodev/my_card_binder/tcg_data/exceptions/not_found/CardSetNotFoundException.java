package org.gatodev.my_card_binder.tcg_data.exceptions.not_found;

public class CardSetNotFoundException extends TCGDataEntityNotFoundException {
    public CardSetNotFoundException(Long id) {
        super("Card Set", id);
    }
}
