package com.example.menstrualender.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import com.example.menstrualender.model.Cycles;
import javafx.util.converter.LocalDateStringConverter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class StatUtil {

    public static int getAverageInterval(ArrayList<LocalDate> cycles) {

        int intervals = 0;

        for (int i = 0; i < cycles.size(); i++) {
            LocalDate start = cycles.get(i);
            LocalDate end = cycles.get(i + 1);

            // Calculate Number of Days Between two LocalDate objects
            long numberOfDays = ChronoUnit.DAYS.between(start, end);
            //System.out.println("Number of days :" + numberOfDays);
            intervals += (int)numberOfDays;
        }

        return intervals/(cycles.size() + 1); //integer division -> rounds to the nearest integer
    }

    public LocalDate nextCycleStart(int averageInterval, ArrayList<LocalDate> cycles) {

        int latestEntry = cycles.size() - 1;
        LocalDate lastEntry = cycles.get(latestEntry);
        
        return lastEntry.plusDays(averageInterval);
    }

}
