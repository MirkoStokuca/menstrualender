package mvp;

public class Data {
    // Attribute
    String date;

    public Data(String date) {
        this.date = date;
    }

    public Data() {

    }

    public void printDate(){
        System.out.println("Eingabe Datum Tag der ersten Blutung: " + date);
    }
}
