package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import com.example.menstrualender.util.DateUtil;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.menstrualender.MensApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MensController implements Initializable {
    //Declarations
    @FXML
    private Label kalenderAusgabe;
    @FXML
    private Label buttonConf;
    @FXML
    private Label nextCycleStart;
    @FXML
    private Label averageInterval;
    @FXML
    private PieChart cycleGraph;
    @FXML
    private LineChart tempGraph;
    @FXML
    private StackedBarChart generalGraph;
    @FXML
    private DatePicker datePicker;
    @FXML
    private StackedBarChart stackedBarChart;

    @FXML
    private MensApplication mensApp;

    Db db = new Db();
    @FXML
    Cycles zyklus = new Cycles(db);




    /**
     * Constructor
     */
    public MensController() {
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * Init
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Init pie chart
        initPieChart();

        //Init stacked bar chart
        initStackedBarChart();
        loadStackedBarChart();
    }

    /**
     * Init stacked bar chart with empty data
     */
    private void initStackedBarChart(){
        stackedBarChart.getXAxis().setOpacity(0); //hide x axis
        stackedBarChart.getYAxis().setOpacity(0); //hide y axis

        stackedBarChart.getStylesheets().add(getClass().getResource("stacked_bar_chart.css").toExternalForm());
    }

    /**
     * Load stored data in db into stacked bar chart
     */
    private void loadStackedBarChart(){

        ResultSet rscounter = zyklus.getCounterHistory();
        int dbRows;
        try {
            do {
                dbRows = rscounter.getInt("counter");
            } while (rscounter.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dbRows);

        //create Series Instances
        XYChart.Series series1= new XYChart.Series();
        XYChart.Series series2= new XYChart.Series();
        XYChart.Series series3= new XYChart.Series();
        XYChart.Series series4= new XYChart.Series();

        //fill series with placeholder values
        for (int i = 11; i > dbRows; i--) {
            String placeholder = "Placeholder" + i;

            series1.getData().add(new XYChart.Data(0,placeholder));
            series2.getData().add(new XYChart.Data(0,placeholder));
            series3.getData().add(new XYChart.Data(0,placeholder));
            series4.getData().add(new XYChart.Data(0, placeholder));
        }

        ResultSet rs = zyklus.getCyclesIntervals();
        int bleeding_days, second_interval, fertility_days, fourth_interval;
        String start_date;

        try {
            do {
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
            } while (rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //add series to stackedBarChart
        stackedBarChart.getData().addAll(series1, series2, series3, series4);
    }

    /**
     * Init pie chart
     */
    private void initPieChart(){
        ObservableList<PieChart.Data>cycleChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("", 15),
                        new  PieChart.Data("", 8));
        cycleGraph.setData(cycleChartData);
        cycleGraph.autosize();
        cycleGraph.setStartAngle(-100);
    }

    @FXML
    public void loadData() {
        zyklus.readData();
    }

    /**
     * Deletes the Data in the File
     * Naja, möchte vielleicht nicht immer alle Daten auf einmal löschen.
     * direkt auf Datenbank anpassen.
     */
    @FXML
    private void deleteData() {
        zyklus.deleteData();
        mensApp.showDefaultWindow();
    }

    /**
     * saves Data into File
     */
    public void saveData() {

    }

    /**
     * Shows Average Interval and Next Cycle Start
     */
    @FXML
    public void showInfos() {
        showAverageInterval();
        // Todo: printCalender macht noch Fehler, deshalb ist der auskommentiert
        //printCalender();
        //showAverageInterval();
    }

    /**
     * Changes Label averageInterval in hello-view to averageInterval from Cycles
     * returns Int averageInterval
     */

    public void showAverageInterval() {
        //int averInterval = zyklus.getAverageInterval();
        //int averInterval = (zyklus.getAverageInterval());
        //averageInterval.setText(Integer.toString(averInterval) + " days");
    }

    /**
     * Changes Label nextCycleStart in hello-view to nextCycleStart from Cycles
     * takes int "averageInterval"
     *//*
    public void showNextCycleStart() {
        nextCycleStart.setText(zyklus.calculateNextCycleStart());
    }

    *//**
     * Sets Text of Label kalenderAusgabe to the Calender Infos
     */
    public void printCalender() {
        String message = "Saved dates:\n\n";
        ResultSet rs = zyklus.getCycles();
        try {
            if(!rs.next()) { // false Check! rs.next() == false
                message += "None Found!\n\nHow to Add New Cycle:\n1. Choose Date\n2.\"Start new Cycle\"" +
                        "\n\nAdd at least two dates\nto calulate the start\nof the next cycle.";
            } else {
                do {
                    message += LocalDate.parse(rs.getString("cyc_date_start")).format(DateUtil.formatterLong) + "\n";
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ToDo: Kalenderausgabe macht fehler, deshalb wird die Funktion nicht aufgerufen

        //kalenderAusgabe.setText(message);
    }

    public void cyclesDetailLength() {
        String message = "";
        int bleeding_days, second_interval, fertility_days, fourth_interval;
        LocalDate start_date;
        ResultSet rs = zyklus.getCyclesIntervals();
        try {
            if(!rs.next()) { // false Check! rs.next() == false
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

    public void switchToLogin() {
        mensApp.loginWindow();
        /*zyklus.readData();
        showAverageInterval();
        printCalender();
        showNextCycleStart();*/
    }

    public void switchToDaily(){
        mensApp.showDailyWindow();

    }

    public void switchToMonthly(){
        mensApp.showMonthlyWindow();
    }

    public void switchToSettings(){
    }

    /**
     * Takes the input from the datePicker and saves it in cycle
     */
    @FXML
    public void setDate() {
        try {
            LocalDate myDate = datePicker.getValue();
            myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            zyklus.addDate(myDate);
            showButton("Cycle added", Color.GREEN);
        } catch (NullPointerException e) {

            showButton("Pick a Date", Color.RED);
        }
    }

    /**
     * Makes Text appear and Disappear. Takes label String and color (true = red, false = green)
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
     * sets fade Parameter
     * @param node
     * @return fade
     */
    private FadeTransition createFader(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(100);
        fade.setToValue(0);

        return fade;
    }
}