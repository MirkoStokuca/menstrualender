package com.example.menstrualender.util;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static DateTimeFormatter formatterLong = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    public static DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("dd.MM.yyyy");

}