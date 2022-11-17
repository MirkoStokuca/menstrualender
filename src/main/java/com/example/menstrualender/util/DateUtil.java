package com.example.menstrualender.util;

import java.time.format.DateTimeFormatter;

/**
 * Formatting
 */
public class DateUtil {
    public static DateTimeFormatter formatterLong = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    public static DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("dd.MM.yyyy");

}