package com.example.menstrualender.model;

import com.example.menstrualender.MensApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Cycles {

    public ArrayList<LocalDate> cycles;

    /**
     * reads the .csv file and saves the found dates as
     * LocalDate objects in the cycles Arraylist
     */
    public void readData() {

        ArrayList<LocalDate> readDates = new ArrayList<>();
        try {
            Path path = Paths.get(MensApplication.PATH_TO_FILE);
            for(String valueLine : Files.readAllLines(path)){
                String[] val = valueLine.split(";");
                for (int i = 0; i < val.length; i++) {
                    var tempStringArray = val[i].split("\\-");
                    for (int j = 0; j < tempStringArray.length; j++) {
                        int day = Integer.parseInt(tempStringArray[2]);
                        int month = Integer.parseInt(tempStringArray[1])-1;
                        int year = Integer.parseInt(tempStringArray[0]);
                        readDates.add(LocalDate.of(year,month,day));
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei.");
            System.err.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Es wurden " + readDates.size()/3 + " Einträge generiert");
        System.out.println();
        cycles = readDates;
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

    public void addDate(LocalDate date) {
        /**
         * add a new date to the cycles vector, catch input errors
         * format: dd.mm.yyyy or dd-mm-yyyy or dd/mm/yyyy
         */
        cycles.add(date);
    }
    public String getAndCheckInput (String message) {
        Scanner value = new Scanner(System.in);
        System.out.println(message);

        while (true) {
            try {
                String input = value.next();
                var inputArray = input.split("\\.|\\-|\\/");
                return inputArray[0] + "." + inputArray[1] + "." + inputArray[2];
            } catch (Exception e) {
                System.out.println("Falsche Eingabe!");
            }
        }
    }
}