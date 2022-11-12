package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import com.example.menstrualender.util.DateUtil;
import javafx.animation.FadeTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import com.example.menstrualender.MensApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class MensController implements Initializable {
    //Declarations
    @FXML
    private Label kalenderAusgabe;
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
    @FXML
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
        ObservableList<PieChart.Data>cycleChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("", 15),
                        new  PieChart.Data("", 8));
        cycleGraph.setData(cycleChartData);
        cycleGraph.autosize();
        cycleGraph.setStartAngle(-100);

        //Init stacked bar chart
        XYChart.Series series1= new XYChart.Series(); //create Series Instance
        series1.setName("Vor Blutung"); //display as legend
        series1.getData().add(new XYChart.Data(10, "01.11.2022"));
        series1.getData().add(new XYChart.Data(11, "01.12.2022"));
        series1.getData().add(new XYChart.Data(12, "01.01.2023"));
        series1.getData().add(new XYChart.Data(8, "01.02.2023"));
        series1.getData().add(new XYChart.Data(9, "01.03.2023"));
        series1.getData().add(new XYChart.Data(10, "01.04.2023"));
        series1.getData().add(new XYChart.Data(13, "01.05.2023"));

        XYChart.Series series2= new XYChart.Series(); //create Series Instance
        series2.setName("Blutung"); //display as legend
        series2.getData().add(new XYChart.Data(4, "01.11.2022"));
        series2.getData().add(new XYChart.Data(5, "01.12.2022"));
        series2.getData().add(new XYChart.Data(4, "01.01.2023"));
        series2.getData().add(new XYChart.Data(6, "01.02.2023"));
        series2.getData().add(new XYChart.Data(4, "01.03.2023"));
        series2.getData().add(new XYChart.Data(4, "01.04.2023"));
        series2.getData().add(new XYChart.Data(6, "01.05.2023"));

        XYChart.Series series3= new XYChart.Series(); //create Series Instance
        series3.setName("Nach Blutung"); //display as legend
        series3.getData().add(new XYChart.Data(10,"01.11.2022"));
        series3.getData().add(new XYChart.Data(11, "01.12.2022"));
        series3.getData().add(new XYChart.Data(12, "01.01.2023"));
        series3.getData().add(new XYChart.Data(8, "01.02.2023"));
        series3.getData().add(new XYChart.Data(9, "01.03.2023"));
        series3.getData().add(new XYChart.Data(10, "01.04.2023"));
        series3.getData().add(new XYChart.Data(12, "01.05.2023"));



        stackedBarChart.getData().addAll(series1, series2, series3);


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
        printCalender();
        showAverageInterval();
    }

    /**
     * Changes Label averageInterval in hello-view to averageInterval from Cycles
     * returns Int averageInterval
     */

    public void showAverageInterval() {
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
                    message += LocalDate.parse(rs.getString("cyc_date_start")).format(DateUtil.formatter) + "\n";
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //kalenderAusgabe.setText(message);
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
}