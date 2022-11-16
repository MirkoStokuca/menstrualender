package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import com.example.menstrualender.util.DateUtil;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private LineChart tempGraph;
    @FXML
    private StackedBarChart generalGraph;
    @FXML
    private DatePicker datePicker;
    @FXML
    private StackedBarChart stackedBarChart;

    @FXML
    private AnchorPane resize;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

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

    //Init and Charts

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
        sliderSlide();
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
     * Init Line Chart
     */
    private void initLineChart(){

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //DBHandler dbHandler = new DBHandler();

        /*List<List<Integer>> dataHolder = dbHandler.getDataHolder();
        for(int i = 0; i < dataHolder.size(); i++)
        {
            series.getData().add(new XYChart.Data(dataHolder.get(i).get(0), dataHolder.get(i).get(1)));
        }*/
    }

    /**
     * Init pie chart
     */
    private void initPieChart(){
        //@JULIA
        int nextCycleStart; //Hier prediction datum des nächsten Zyklus (oder letzer Zyklus und avrg aus DB)
        int timUntilNextCycle; // nextCycleStart - aktuelles Datum

        ObservableList<PieChart.Data>cycleChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("", 10),
                        new  PieChart.Data("", 18));
        cycleGraph.setData(cycleChartData);
        cycleGraph.autosize();
        cycleGraph.setStartAngle(-100);
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

    @FXML
    public void upDateInfos(){
        showAverageInterval();
        showNextCycleStart();
        loadStackedBarChart();
    }


    /**
     * Changes Label averageInterval in hello-view to averageInterval from Cycles
     * returns Int averageInterval
     */

    public void showAverageInterval() {
        //@JULIA hier bitte richtig (parsen?) wert muss averageInterval übergeben werden
        //averageInterval = zyklus.getAverageLength();
    }

    /**
     * Changes Label nextCycleStart in hello-view to nextCycleStart from Cycles
     * takes int "averageInterval"
     */

    public void showNextCycleStart() {
        //@JULIA berechnung nächstes Zyklus start datum
        //nextCycleStart =
    }

    public static void main(String[] args) {
        MensController m = new MensController();
        m.cyclesDetailLength();

    }

    //Scene/Stage Switches
    public void switchToLogin() {
        mensApp.loginWindow();
    }

    public void switchToDaily(){
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
            myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            //@JULIA Hier myDate (gewähltes Datum) an DB weitergeben

            showButton("Cycle added", Color.GREEN);
        } catch (NullPointerException e) {
            showButton("Pick a Date", Color.RED);
        }
    }

    //Animations

    private void sliderSlide(){
        slider.setTranslateX(-300);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-300);

            slide.setOnFinished((ActionEvent e)-> {
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

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);

            });
        });
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


    //Datenbank Beispiel
    public void cyclesDetailLength() {
        String message = "";
        int bleeding_days, second_interval, fertility_days, fourth_interval, cycle_length, anzahl_cycles = 0;
        LocalDate start_date;
        ResultSet rs = zyklus.getCyclesHitstoryIntervals();
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
                    cycle_length = rs.getInt("cycle_length");
                    anzahl_cycles++;
                    System.out.println(bleeding_days);
                    System.out.println(second_interval);
                    System.out.println(fertility_days);
                    System.out.println(fourth_interval);
                    System.out.println(fourth_interval);
                    System.out.println(start_date);
                    System.out.println(cycle_length);
                    System.out.println(anzahl_cycles);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}