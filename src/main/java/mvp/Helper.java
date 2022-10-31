package mvp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Helper {

    public ArrayList<Cycle> cycles;

    public void printCycles(){
        /**
         * prints the dates in the cycles array
         */
        for (var cycle : cycles) {
            cycle.printDate();
        }
    }

    public void readData() {
        /**
         * reads the .csv file and saves the found dates as
         * Gregorian Calendar objects in the cycles Arraylist
         */
        ArrayList<Cycle> readDates = new ArrayList<Cycle>();
        try {
            Path path = Paths.get(Main.PATH_TO_FILE);
            for(String valueLine : Files.readAllLines(path)){
                String[] val = valueLine.split(";");
                for (int i = 0; i < val.length; i++) {
                    readDates.add(new Cycle(parseToGregorian(val[i])));
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei.");
            System.err.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Es wurden " + readDates.size() + " Einträge generiert");
        System.out.println();
        cycles = readDates;
    }
    public void saveData() {
        try (var fileWriter = new FileWriter(Main.PATH_TO_FILE)){
            for (var cycle : cycles) {
                fileWriter.write(gregorianToString(cycle.date) + ";");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    public static GregorianCalendar parseToGregorian (String stringDate) {
        /**
         * Format: 11-11-1111
         * Returns Gregorian Calendar
         */
        var splitDate = stringDate.split("\\.");
        int tempDay = Integer.parseInt(splitDate[0]);
        int tempMonth = Integer.parseInt(splitDate[1]) - 1;
        int tempYear = Integer.parseInt(splitDate[2]);
        var parsedDate = new GregorianCalendar();
        parsedDate.set(tempYear, tempMonth, tempDay);

        return parsedDate;
    }

    public static String gregorianToString (GregorianCalendar gregorianDate) {
        /**
         * Returns Formatted String
         */
        int day = gregorianDate.get(Calendar.DATE);
        int month = gregorianDate.get(Calendar.MONTH) + 1;
        int year = gregorianDate.get(Calendar.YEAR);

        return String.format("%02d.%02d.%04d", day, month, year);
    }

    public void addDate() {
        /**
         * add a new date to the cycles vector, catch input errors
         * format: dd.mm.yyyy or dd-mm-yyyy or dd/mm/yyyy
         */
    // [ ] Es fehlt das schreiben in eine Datei. Also das abspeichern
        String message = "Zyklus Anfangsdatum Eingeben (ddmmyyyy, erlaubte Trennzeichen \".\", \"-\" oder \"/\"): ";
        cycles.add(new Cycle(parseToGregorian(getAndCheckInput(message))));
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