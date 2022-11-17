package com.example.menstrualender.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Cycles {

    private Db db;

    public Cycles(Db db) {
        this.db = db;
    }

    public Cycles() {
    }

    public ResultSet getCycles() {
        ResultSet rs = this.db.getCycles();
        return rs;
    }
    public ResultSet getCyclesHitstoryIntervals() {
        ResultSet rs = this.db.getCyclesHistoryIntervals();
        return rs;
    }
    public ResultSet getCounterHistory() {
        ResultSet rs = this.db.getCountHistoryCycles();
        return rs;
    }
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

    public void addOutflow(int value) {
        this.db.insertOutflow(value);
    }
    public void addMood(int value) {
        this.db.insertMood(value);
    }
    public void addTemp(String value) {
        this.db.insertTemperature(value);
    }
    public void addComments(String comment) {
        this.db.insertComment(comment);
    }
    public void addBleeding(int value) {
        this.db.insertBleeding(value);
    }
    public void addOvulation(LocalDate date) {
        this.db.insertOvulation(date);
    }

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

    public ResultSet getTemperatur() {
        return this.db.getTemperatur();
    }
}
