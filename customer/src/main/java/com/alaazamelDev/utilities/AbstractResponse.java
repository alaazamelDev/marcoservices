package com.alaazamelDev.utilities;

import java.time.LocalDateTime;

public abstract class AbstractResponse {

    public static String formatDate(LocalDateTime dateTime) {
        return LocalDateFormatter.formatDate(dateTime);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return LocalDateFormatter.formatDateTime(dateTime);
    }
}
