package com.example.menstrualender.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * bridge between Db and Fxml
 */
public class Cycles {

    //deklaration
    private Db db;

    public Cycles(Db db) {
        this.db = db;
    }

    public Cycles() {
    }

    /**
     * returns last cycle
     * @return
     */
    public ResultSet getCycles() {
        ResultSet rs = this.db.getCycles();
        return rs;
    }

    /**
     * returns all Last Cycles
     * @return
     */
    public ResultSet getCyclesHitstoryIntervals() {
        ResultSet rs = this.db.getCyclesHistoryIntervals();
        return rs;
    }

    /**
     * returns counter History
     * @return
     */
    public ResultSet getCounterHistory() {
        ResultSet rs = this.db.getCountHistoryCycles();
        return rs;
    }

    /**
     * returns calculated predicted Cycle
     * @return
     */
    public ResultSet getPredictionCycle() {
        ResultSet rs = this.db.getPredictionCycle();
        return rs;
    }

    /**
     * add a new date to the cycles Database
     * @param date LocalDate object
     */
    public void addDate(LocalDate date) {
        this.db.insertCycle(date);
    }

    /**
     * add a new Flow data to the cycles Database
     * @param value
     */
    public void addOutflow(int value) {
        this.db.insertOutflow(value);
    }

    /**
     * add a new Mood data to the cycles Database
     * @param value
     */
    public void addMood(int value) {
        this.db.insertMood(value);
    }

    /**
     * add a new Temperature Data to the cycles Database
     * @param value
     */
    public void addTemp(String value) {
        this.db.insertTemperature(value);
    }

    /**
     * add a new Comment Data to the cycles Database
     * @param comment
     */
    public void addComments(String comment) {
        this.db.insertComment(comment);
    }

    /**
     * add a new bleeding data to the cycles Database
     * @param value
     */
    public void addBleeding(int value) {
        this.db.insertBleeding(value);
    }

    /**
     * add a new Ovulation data to the cycles Database
     * @param date
     */
    public void addOvulation(LocalDate date) {
        this.db.insertOvulation(date);
    }

    /**
     * calls method to delete all the Data in Db
     */
    public void deleteData(){
        this.db.deleteCycle();        // in der Klammer könnte die id_cyc mit gegeben werden, um ein bestimmten Eintrag zu löschen
    }

    /**
     * calculates the average interval in between the dates in
     * the cycles Datenbank
     * @return average interval
     */
    public String getAverageLength() {
        try {
            return this.db.getAvg().getString("cycle_avg_days");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * returns the Temperature from Db
     * @return
     */
    public ResultSet getTemperatur() {
        return this.db.getTemperatur();
    }
}
