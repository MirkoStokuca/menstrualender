package com.example.menstrualender.view;

import com.example.menstrualender.model.Db;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private MensApplication mensApp;
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;


    /**
     * Constructor
     */
    public MensController() {
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
        //Init stacked bar chart
        sliderSlide();
        initPieChart();
        initStackedBarChart();
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
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();

        //fill series with placeholder values
        for (int i = 11; i > dbRows; i--) {
            String placeholder = "Placeholder" + i;

            series1.getData().add(new XYChart.Data(0, placeholder));
            series2.getData().add(new XYChart.Data(0, placeholder));
            series3.getData().add(new XYChart.Data(0, placeholder));
            series4.getData().add(new XYChart.Data(0, placeholder));
        }

        ResultSet rs = this.mensApp.zyklus.getCyclesHitstoryIntervals();
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
                series1.getData().add(new XYChart.Data(bleeding_days, start_date));
                series2.getData().add(new XYChart.Data(second_interval, start_date));
                series3.getData().add(new XYChart.Data(fertility_days, start_date));
                series4.getData().add(new XYChart.Data(fourth_interval, start_date));
            } while (rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //add series to stackedBarChart
        stackedBarChart.getData().addAll(series1, series2, series3, series4);
    }

    /**
     * Init Line Chart
     */
    private void initLineChart() {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

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
    private void initPieChart() {
        /**
         * @Mirko, aktuelles Datum könntest du bitte heraussuchen, ja?
         *
         * jetzt ist alles Grau well die Variabeln nicht gebracuht werden.
         * du musst nicht alle Variabeln brauchen, sie es eher als Variabeln Buffet :)
         */
        // prediction:
        int preSecond_interval = 0;
        int preFertility_days = 0;
        int preFourth_interval = 0;
        // facts in last 12 months:
        int avg_cyc_length = 0;
        int avg_bleeding_length = 0;
        int min_cyc_length = 0;
        int max_cyc_length = 0;

        LocalDate start_date, nextCycleStart;
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

                    /**
                     * Todo: @julia; end_cycle is null
                     */
                    //nextCycleStart = LocalDate.parse(rs.getString("end_cycle"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<PieChart.Data> cycleChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Bleeding", avg_bleeding_length),
                        new PieChart.Data("", preSecond_interval),
                        new PieChart.Data("Fruchtbar", preFertility_days),
                        new PieChart.Data("", preFourth_interval));

        this.cycleGraph.setLabelLineLength(20);
        this.cycleGraph.setData(cycleChartData);
        this.cycleGraph.setLabelsVisible(true);
        this.cycleGraph.setStartAngle(-100);
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

    @FXML
    public void upDateInfos() {
        showAverageInterval();
        showNextCycleStart();
        loadStackedBarChart();
    }

    public void showAverageInterval() {
        averageInterval.setText(this.mensApp.zyklus.getAverageLength());
    }

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
        nextCycleStart.setText(startNextCycle);
    }


    //Scene/Stage Switches
    public void switchToLogin() {
        mensApp.loginWindow();
    }

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
            myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            //Start:
            this.mensApp.zyklus.addDate(myDate);
            showButton("Cycle added", Color.GREEN);
        } catch (NullPointerException e) {
            showButton("Pick a Date", Color.RED);
        }
    }

    //Animations

    private void sliderSlide() {
        slider.setTranslateX(-300);
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


    //Datenbank Beispiel
    public void cyclesDetailLength() {
        String message = "";
        int bleeding_days, second_interval, fertility_days, fourth_interval;
        LocalDate start_date;
        ResultSet rs = mensApp.zyklus.getCyclesHitstoryIntervals();
        try {
            if (!rs.next()) { // false Check! rs.next() == false
                message += "None Found!\n\nHow to Add New Cycle:\n1. Choose Date\n2.\"Start new Cycle\"" +
                        "\n\nAdd at least two dates\nto calulate the start\nof the next cycle.";
            } else {
                do {
                    bleeding_days = rs.getInt("first_interval");
                    second_interval = rs.getInt("second_interval");
                    fertility_days = 7;
                    fourth_interval = rs.getInt("fourt_interval");
                    start_date = LocalDate.parse(rs.getString("start_cycle"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}