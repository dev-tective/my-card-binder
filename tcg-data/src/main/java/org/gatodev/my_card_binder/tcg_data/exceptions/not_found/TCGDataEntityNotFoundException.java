package org.gatodev.my_card_binder.tcg_data.exceptions.not_found;

import lombok.Getter;

@Getter
public class TCGDataEntityNotFoundException extends RuntimeException {
    private final String entityName;
    private final Long id;

    public TCGDataEntityNotFoundException(String entityName, Long id) {
        super(String.format("%s not found with ID: %s", entityName, id));
        this.entityName = entityName;
        this.id = id;
    }
}
