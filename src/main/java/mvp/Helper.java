package mvp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Administer {

    public ArrayList<Cycle> initialDate;

    protected void printInitialDate(ArrayList<Cycle> initialDate){
        for (Cycle d : initialDate) {
            d.printDate();
        }
    }

    public void readData() {
        ArrayList<Cycle> date = new ArrayList<Cycle>();
        try {
            Path path = Paths.get("artificialData.txt");
            for(String valueLine : Files.readAllLines(path)){
                String[] val = valueLine.split(";");
                date.add(new Cycle(val[0]));
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei.");
            System.err.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Es wurden " + date.size() + " Einträge generiert");
        System.out.println();
        initialDate = date;
    }


    public void inputDate() {
    // [ ] Es fehlt das schreiben in eine Datei. Also das abspeichern.
        ArrayList<Cycle> date = new ArrayList<Cycle>();
        String initialValue;

        Scanner value = new Scanner(System.in);
        System.out.println("Zyklus Anfangsdatum Eingeben: ");
        initialValue = value.next();
        date.add(new Cycle(initialValue));
    }
}
