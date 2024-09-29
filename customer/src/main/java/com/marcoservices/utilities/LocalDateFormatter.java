package com.marcoservices.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Utility method to format LocalDate
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER) : null;
    }

    // Utility method to format LocalDateTime
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }
}
