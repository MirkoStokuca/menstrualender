package com.example.menstrualender.model;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.util.DateUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Cycles {

    public ArrayList<LocalDate> cycles = new ArrayList<>();

    /**
     * reads the .csv file and saves the found dates as
     * LocalDate objects in the cycles Arraylist
     * returns cycles ArrayList of Cycle objects
     */
    public void readData() {

        ArrayList<LocalDate> readDates = new ArrayList<>();
        try {
            Path path = Paths.get(MensApplication.PATH_TO_FILE);
            for(String valueLine : Files.readAllLines(path)){
                String[] val = valueLine.split(";");
                for (String s : val) {

                    var tempStringArray = s.split("-");
                    int year = Integer.parseInt(tempStringArray[0]);
                    int month = Integer.parseInt(tempStringArray[1]);
                    int day = Integer.parseInt(tempStringArray[2]);
                    readDates.add(LocalDate.of(year, month, day));

                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei.");
            System.err.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Es wurden " + readDates.size() + " Einträge generiert");
        System.out.println();
        if (!cycles.isEmpty()) cycles.clear();
        cycles = readDates;
    }

    public ArrayList<LocalDate> getCycles() {
        return cycles;
    }

    public void saveData() {
        try (var fileWriter = new FileWriter(MensApplication.PATH_TO_FILE)){
            for (var cycle : cycles) {
                fileWriter.write(cycle + ";");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * add a new date to the cycles Arraylist
     * @param date LocalDate object
     */
    public void addDate(LocalDate date) {
        cycles.add(date);
    }

    public void deleteData(){
        cycles.clear();
        saveData();
    }

    /**
     * calculates the average interval in between the dates in
     * the cycles ArrayList
     * @return average interval
     */
    public int getAverageInterval() {

        long intervals = 0;

        for (int i = 0; i < (cycles.size() - 1); i++) {
            LocalDate start = cycles.get(i);
            LocalDate end = cycles.get(i + 1);

            // Calculate Number of Days Between two LocalDate objects
            long numberOfDays = ChronoUnit.DAYS.between(start, end);
            //System.out.println("Number of days :" + numberOfDays);
            intervals += numberOfDays;
        }

        double resultOfDivision = (double) intervals/(cycles.size()-1);

        return (int) Math.round(resultOfDivision);
    }

    /**
     * Calculates the date of the start of the next cycle
     * @return LocalDate with averageInterval added
     */
    public String calculateNextCycleStart() {

        int averageInterval = getAverageInterval();

        try {
            LocalDate lastEntry = this.cycles.get(cycles.size() - 1);
            return lastEntry.plusDays(averageInterval).format(DateUtil.formatter);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}