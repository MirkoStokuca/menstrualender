package mvp;
public class Main {
    final static String PATH_TO_FILE = "src/artificialData.csv";
    public static void main(String[] args) {
        Helper helper = new Helper();
        helper.readData();
        helper.printCycles();

        helper.addDate();
        helper.printCycles();

        helper.saveData();




    }
}
