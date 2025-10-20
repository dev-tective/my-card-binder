package org.gatodev.my_card_binder.tcg_data.exceptions;

import java.time.LocalDateTime;

public record ErrorDetails(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public ErrorDetails(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now());
    }
}
