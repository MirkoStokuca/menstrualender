package com.example.menstrualender.view;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.menstrualender.MensApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MensController implements Initializable {
    //Declarations;
    @FXML
    private Label buttonConf;
    @FXML
    private Label nextCycleStart;
    @FXML
    private Label averageInterval;
    @FXML
    private PieChart cycleGraph;
    @FXML
    private LineChart lineChart;
    @FXML
    private CategoryAxis BOTTOM;
    @FXML
    private NumberAxis LEFT;
    @FXML
    private StackedBarChart generalGraph;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label todaysDate;
    @FXML
    private StackedBarChart stackedBarChart;
    @FXML
    private MensApplication mensApp;
    @FXML
    private Label Menu;
    @FXML
    private Label MenuBack;
    @FXML
    private ToggleButton pregnantMode;
    @FXML
    private AnchorPane slider;
    public String colorFruchtbar = "MEDIUMPURPLE";
    public ObservableList<PieChart.Data> cycleChartData;


    /**
     * Constructor
     */
    public MensController() {
    }

    /**
     * Starts scene and calls visual output
     * @param mensApp
     */
    public void setMainApp(MensApplication mensApp) throws ParseException {
        this.mensApp = mensApp;
        //Init stacked bar chart
        sliderSlide();
        initPieChart();
        initStackedBarChart();
        initLineChart();
        //loadStackedBarChart();
    }

    //Init and Charts

    /**
     * Init
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init pie chart
    }

    /**
     * Init stacked bar chart with empty data
     */
    private void initStackedBarChart() {
        stackedBarChart.getXAxis().setOpacity(0); //hide x axis
        stackedBarChart.getYAxis().setOpacity(0); //hide y axis
        stackedBarChart.getStylesheets().add(getClass().getResource("stacked_bar_chart.css").toExternalForm());
    }

    /**
     * Load stored data in db into stacked bar chart
     */
    private void loadStackedBarChart() {

        ResultSet rscounter = this.mensApp.zyklus.getCounterHistory();
        int dbRows = 0;
        try {
            while (rscounter.next()) {
                dbRows = rscounter.getInt("counter");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //create Series Instances
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Blutung");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Follikelphase");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Fruchtbar");
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Lutealphase");

        // delete Data
        if (stackedBarChart != null) {
            series1.getData().removeAll();
            series2.getData().removeAll();
            series3.getData().removeAll();
            series4.getData().removeAll();
        }

        //fill series with placeholder values
        for (int i = 11; i > dbRows; i--) {
            String placeholder = "Placeholder" + i;

            series1.getData().add(new XYChart.Data(0, placeholder));
            series2.getData().add(new XYChart.Data(0, placeholder));
            series3.getData().add(new XYChart.Data(0, placeholder));
            series4.getData().add(new XYChart.Data(0, placeholder));
        }

        ResultSet rs = this.mensApp.zyklus.getCyclesHistoryIntervals();
        int bleeding_days, second_interval, fertility_days, fourth_interval;
        String start_date;


        try {
            while (rs.next()) {
                bleeding_days = rs.getInt("first_interval");
                second_interval = rs.getInt("second_interval");
                fertility_days = 7;
                fourth_interval = rs.getInt("fourth_interval");
                start_date = rs.getString("start_cycle");


                //add db datd to series
                series1.getData().add(new XYChart.Data(bleeding_days,start_date));
                series2.getData().add(new XYChart.Data(second_interval,start_date));
                series3.getData().add(new XYChart.Data(fertility_days,start_date));
                series4.getData().add(new XYChart.Data(fourth_interval,start_date));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stackedBarChart.getData().clear();
        //add series to stackedBarChart
        stackedBarChart.getData().addAll(series1, series2, series3, series4);
    }

    /**
     * Init Line Chart
     */
    private void initLineChart() {
        //change Title
        lineChart.setTitle("Temperaturkurve");

        //defining a series
        XYChart.Series series = new XYChart.Series();

        //style lineChart
        lineChart.getStylesheets().add(getClass().getResource("line_chart.css").toExternalForm());

        //add information into series
        for(int i = 0; i < getArrayListFloatTemperaturData().size(); i++)
        {
            series.getData().add(new XYChart.Data(i,getArrayListFloatTemperaturData().get(i)));
        }
        //load series into linechart
        lineChart.getData().addAll(series);
    }


    public ArrayList<Float> getArrayListFloatTemperaturData() {
        ArrayList<Float> arrayTemperatur = new ArrayList<>();
        float oneTemperatur;
        ResultSet rs = mensApp.zyklus.getTemperature();
        try {
            if (!rs.next()) {
                System.out.println("keine Temperatur eingaben");
            } else {
                do {
                    oneTemperatur = rs.getFloat("temperature_value");
                    arrayTemperatur.add(oneTemperatur);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hole Daten aus der Datenbank");
        System.out.println(arrayTemperatur);
        return arrayTemperatur;
    }

    /**
     * Hier steht die Outflow Arraylist für eine beliebige ausgabe zur verfügung
     * viel spass :)
     *
     */
    public ArrayList<Integer> getArrayListOutflow() {
        ArrayList<Integer> arrayOutflow = new ArrayList<>();
        int oneOutflow;
        ResultSet rs = mensApp.zyklus.getOutflow();
        try {
            if (!rs.next()) {
                System.out.println("keine Temperatur eingaben");
            } else {
                do {
                    oneOutflow = rs.getInt("outflow");
                    arrayOutflow.add(oneOutflow);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hole Daten aus der Outflow Tabelle | Datenbank");
        System.out.println(arrayOutflow);
        return arrayOutflow;
    }

    /**
     * Init pie chart
     */

    private void initPieChart() throws ParseException {
        // prediction:
        int preSecond_interval = 0;
        int preFertility_days = 0;
        int preFourth_interval = 0;
        // facts in last 12 months:
        int avg_cyc_length = 0;
        int avg_bleeding_length = 0;
        int min_cyc_length = 0;
        int max_cyc_length = 0;

        LocalDate start_date, nextCycleStart, fertility_interval_start = null, fertility_interval_end = null;
        ResultSet rs = mensApp.zyklus.getPredictionCycle();
        try {
            if (!rs.next()) { // false Check! rs.next() == false
                System.out.println("Prediction Cycle: Db, hat keine Daten");
            } else {
                do {
                    avg_bleeding_length = rs.getInt("avg_bleeding_days_in_last_12_months");
                    avg_cyc_length = rs.getInt("avg_cycle_length_in_last_12_months");
                    preSecond_interval = rs.getInt("second_interval");
                    preFertility_days = rs.getInt("fertility_length");
                    preFourth_interval = rs.getInt("fourth_interval");
                    start_date = LocalDate.parse(rs.getString("start_cycle"));
                    fertility_interval_start = LocalDate.parse(rs.getString("fertility_start"));
                    fertility_interval_end = LocalDate.parse(rs.getString("fertility_end"));
                    nextCycleStart = LocalDate.parse(rs.getString("end_cycle"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat userValue = new SimpleDateFormat("yyyy-MM-dd");
        String fertilityEnd = format.format(userValue.parse(String.valueOf(fertility_interval_end)));
        String fertilityStart = format.format(userValue.parse(String.valueOf(fertility_interval_start)));
       cycleChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Blutung", avg_bleeding_length),
                        new PieChart.Data("Follikelphase", preSecond_interval),
                        new PieChart.Data("Fruchtbare Zeit "+"\n"+ fertilityStart+" - "+ fertilityEnd, preFertility_days),
                        new PieChart.Data("Lutealphase", preFourth_interval));

        this.cycleGraph.setLabelLineLength(8);
        this.cycleGraph.setLabelsVisible(true);
        this.cycleGraph.setStartAngle(90);
        this.cycleGraph.setData(cycleChartData);
        applyCustomColorSequence(
                cycleChartData,
                "ORCHID",
                "lightblue",
                colorFruchtbar,
                "lightblue");
    }
    @FXML
    private void getPregnantMode(){
        if(pregnantMode.isSelected()){
            colorFruchtbar = "forestgreen";
        }
        else {colorFruchtbar = "firebrick";}
        applyCustomColorSequence(
                cycleChartData,
                "ORCHID",
                "lightblue",
                colorFruchtbar,
                "lightblue");
    }




    /**
     * Deletes the Data in the File
     * Naja, möchte vielleicht nicht immer alle Daten auf einmal löschen.
     * direkt auf Datenbank anpassen.
     */
    @FXML
    private void deleteData() {
        this.mensApp.zyklus.deleteData();
        this.mensApp.showDefaultWindow();
    }

    /**
     * updates Grafik output
     */
    @FXML
    public void upDateInfos() throws ParseException {
        showAverageInterval();
        showNextCycleStart();
        loadStackedBarChart();
        todaysDate();
        initPieChart();

    }

    /**
     * shows Average Interval on scene
     */
    public void showAverageInterval() {
        final DecimalFormat avgFormat = new DecimalFormat("0");
        averageInterval.setText(avgFormat.format(Double.parseDouble(this.mensApp.zyklus.getAverageLength()))+" Tage");
    }

    /**
     * shows next Cycle start on scene
     */
    public void showNextCycleStart() {
        ResultSet rs = mensApp.zyklus.getPredictionCycle();
        String startNextCycle = null;
        try {
            if (!rs.next()) { // false Check! rs.next() == false
                System.out.println("Prediction Datenbank: keinen Inhalt");
            } else {
                do {
                    startNextCycle = rs.getString("end_cycle");
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
           SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
           SimpleDateFormat userValue = new SimpleDateFormat("yyyy-MM-dd");
           nextCycleStart.setText(format.format(userValue.parse(startNextCycle)));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void todaysDate() throws ParseException {
        LocalDate dateToday = LocalDate.now();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat userValue = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(userValue.parse(String.valueOf(dateToday)));
        todaysDate.setText("Heute ist der: "+ today);
    }


    /**
     * calls open LoginWindow
     */
    public void switchToLogin() {
        mensApp.loginWindow();
    }

    /**
     * calls showDailyWindow
     */
    public void switchToDaily() {
        mensApp.showDailyWindow();
    }


    // Action events

    /**
     * Takes the input from the datePicker and saves it in cycle
     */
    @FXML
    public void setDate() {
        try {
            LocalDate myDate = datePicker.getValue();
            myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.GERMAN));
            this.mensApp.zyklus.addDate(myDate);
            showButton("Zyklus hinzugefügt", Color.GREEN);
        } catch (NullPointerException e) {
            showButton("Datum wählen", Color.RED);
        }
    }

    //Animations

    private void sliderSlide() {
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-300);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-300);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);

            });
        });
    }

    /**
     * sets fade Parameter
     *
     * @param node
     * @return fade
     */
    private FadeTransition createFader(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(100);
        fade.setToValue(0);
        return fade;
    }

    /**
     * Makes Text appear and Disappear. Takes label String and color (true = red, false = green)
     *
     * @param labelText is the output text
     * @param color
     */
    private void showButton(String labelText, Color color) {

        buttonConf.setTextFill(color);
        buttonConf.setText(labelText);
        FadeTransition fader = createFader(buttonConf);
        fader.play();
    }

    /**
     * Creates custom color sequence for piechart
     * @param cycleChartData
     * @param pieColors
     */
    private void applyCustomColorSequence(
            ObservableList<PieChart.Data> cycleChartData,
            String... pieColors) {
        int i = 0;
        for (PieChart.Data data : cycleChartData) {
            data.getNode().setStyle(
                    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
            );
            i++;
        }
    }


    //Datenbank Beispiel
    public void cyclesDetailLength() {
        String message = "";
        int bleeding_days, second_interval, fertility_days, fourth_interval;
        LocalDate start_date;
        ResultSet rs = mensApp.zyklus.getCyclesHistoryIntervals();
        try {
            if (!rs.next()) { // false Check! rs.next() == false
                message += "None Found!\n\nHow to Add New Cycle:\n1. Choose Date\n2.\"Start new Cycle\"" +
                        "\n\nAdd at least two dates\nto calulate the start\nof the next cycle.";
            } else {
                do {
                    bleeding_days = rs.getInt("first_interval");
                    second_interval = rs.getInt("second_interval");
                    fertility_days = 7;
                    fourth_interval = rs.getInt("fourth_interval");
                    start_date = LocalDate.parse(rs.getString("start_cycle"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}