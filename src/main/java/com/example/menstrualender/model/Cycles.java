package com.example.menstrualender.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * bridge between Db and Fxml
 */
public class Cycles {

    //declaration
    private Db db;

    public Cycles(Db db) {
        this.db = db;
    }

    public Cycles() {
    }

    /*
     * returns last cycle
     * we will need this later, to show the user what he wrote in her journal

    public ResultSet getCycles() {
        ResultSet rs = this.db.getCycles();
        return rs;
    }

     */

    /*
     * returns all passed Cycles
     */
    public ResultSet getCyclesHistoryIntervals() {
        return this.db.getCyclesHistoryIntervals();
    }

    /*
     * returns counter History
     */
    public ResultSet getCounterHistory() {
        return this.db.getCountHistoryCycles();
    }

    /*
     * returns calculated predicted Cycle
     */
    public ResultSet getPredictionCycle() {
        return this.db.getPredictionCycle();
    }

    /*
     * add a new date to the cycles Database
     * and check the previous cycle of his validity.
     * By checking if a bleeding day has been entered. If not, it inserts a standard bleeding day.
     * Each new cycle starts with a bleeding day.
     */
    public void addDate(LocalDate date) {
        this.db.insertCycle(date);
        this.db.closeCycle();
    }

    /*
     * add a new Outflow data to the cycles Database
     */
    public void addOutflow(int value) {
        this.db.insertOutflow(value);
    }

    /*
     * add a new Mood data to the cycles Database
     */
    public void addMood(int value) {
        this.db.insertMood(value);
    }

    /*
     * add a new Temperature Data to the cycles Database
     */
    public void addTemp(String value) {
        this.db.insertTemperature(value);
    }

    /*
     * add a new Comment Data to the cycles Database
     */
    public void addComments(String comment) {
        this.db.insertComment(comment);
    }

    /*
     * add a new bleeding data to the cycles Database
     */
    public void addBleeding(int value) {
        int actuelleCycId;
        ResultSet resultSet = this.db.util.query(this.db.SQL_GET_CYC_ID);
        try {
            actuelleCycId = resultSet.getInt("cyc_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.db.insertBleeding(value, actuelleCycId);
    }

    /*
     * add a new Ovulation data to the cycles Database
     */
    public void addOvulation(LocalDate date) {
        this.db.insertOvulation(date);
    }

    /*
     * calls method to delete all the Data in Db
     */
    public void deleteData(){
        this.db.deleteCycle();        // in der Klammer könnte die id_cyc mit gegeben werden, um ein bestimmten Eintrag zu löschen
    }

    /*
     * calculates the average interval in between the dates in
     * the cycles database
     */
    public String getAverageLength() {
        try {
            return this.db.getAvg().getString("cycle_avg_days");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * returns the Temperature from Db
     */
    public ResultSet getTemperature() {
        return this.db.getTemperature();
    }
    public ResultSet getOutflow() {
        return this.db.getOutflow();
    }
}
