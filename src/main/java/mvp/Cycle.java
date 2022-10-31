package mvp;

import java.util.GregorianCalendar;

public class Cycle {
    // Attribute
    GregorianCalendar date;

    public Cycle(GregorianCalendar date) {
        this.date = date;
    }

    public Cycle() {

    }

    public void printDate(){
        System.out.println("Datum der ersten Blutung: " + Helper.gregorianToString(date));
    }
}
