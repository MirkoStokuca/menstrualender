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
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        String [][] stringList = {
                {"01.01.2022", "10", "4", "9"},
                {"01.02.2022", "11", "5", "10"},
                {"01.03.2022", "12", "4", "11"},
                {"01.04.2022", "9", "3", "12"},
                {"01.05.2022", "10", "6", "10"},
                {"01.06.2022", "11", "7", "10"}
        };

        //create Series Instances
        XYChart.Series series1= new XYChart.Series();
        XYChart.Series series2= new XYChart.Series();
        XYChart.Series series3= new XYChart.Series();

        //fill series with placeholder values
        for (int i = 12; i > stringList.length; i--) {
            String placeholder = "Placeholder" + i;

            series1.getData().add(new XYChart.Data(0,placeholder));
            series2.getData().add(new XYChart.Data(0,placeholder));
            series3.getData().add(new XYChart.Data(0,placeholder));
        }

        //add data to series
        for (var cycle : stringList) {

            String tempStartDate = cycle[0];
            int tempBefore = Integer.parseInt(cycle[1]);
            int tempDuring = Integer.parseInt(cycle[2]);
            int tempAfter = Integer.parseInt(cycle[3]);

            series1.getData().add(new XYChart.Data(tempBefore,tempStartDate));
            series2.getData().add(new XYChart.Data(tempBefore,tempStartDate));
            series3.getData().add(new XYChart.Data(tempBefore,tempStartDate));
        }

        //add series to stackedBarChart
        stackedBarChart.getData().addAll(series1, series2, series3);
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
                    message += LocalDate.parse(rs.getString("cyc_date_start")).format(DateUtil.formatterLong) + "\n";
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