package com.example.menstrualender.model;
import com.example.menstrualender.util.DatabaseUtil;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class Db {

    DatabaseUtil dbUtil = new DatabaseUtil();

    protected String SQL_STATS = """
with base as (
    select
        cyc_id
        , date(cyc_start) as this_cycle
        , lag(cyc_start) over (order by date(cyc_start)) as last_cycle
    from cycle
    order by date(cyc_start)
), diff as (
    select
        this_cycle
         , last_cycle
         , julianday(this_cycle) - julianday(last_cycle) as cycle_length
    from base
    where last_cycle is not null
), cycle_avg as (
    select avg(cycle_length) as cycle_avg_days
    from diff
)
""";
    Connection connection;
    Statement statement;

    public Db() {
        boolean noSetFile = false;
        File f = new File("sample.sqlite");
        if (!f.exists()) {          // wenn es die Datenbank noch nicht gibt --> setup
            // noch keine Datenvorhanden, macht Datenbank mit Tabelle verfügbar.
            System.out.println("noch keine Datenvorhanden, macht Datenbank mit Tabelle verfügbar.");
            noSetFile = true;
        }
        this.getConnection();
        if(noSetFile) {
            this.setup();
        }
    }

    private void getConnection() {
        try {
            // create a database connection
            // todo: set password as third argument, to encrypt this database
            // for developing us empty password to easy inspect data with sqlite browser.
            String password = null;
            // String password = "test";
            // wenn es die Datenbank noch nicht gibt, legt er sie mit folgender Zeile an. Sonst macht er damit die connection.
            // Hier könnte der Path angegeben werden, wenn der irgenwo anders als im selben Projekt sein soll.
            this.connection = DriverManager.getConnection("jdbc:sqlite:sample.sqlite", "", password);
            this.statement = this.connection.createStatement();
            this.statement.setQueryTimeout(30);  // set timeout to 30 sec.
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    private void setup() {
        // """ drei macht es zusammenhängend ohne +, ganz vorne schreiben, damit es nicht unnötig zeichen verbraucht.
        //Cycle tabelle erstellen:
        this.dbUtil.update("""
                CREATE TABLE "cycle" (
                "cyc_id"	INTEGER NOT NULL,
                "cyc_start"	TEXT NOT NULL UNIQUE,
                PRIMARY KEY("cyc_id" AUTOINCREMENT)
                );"""
        );
    }



    public int insertCycle(LocalDate date) {
        // statement.executeUpdate("Befehl für datenbank"); Ansprechzeile
        this.dbUtil.update("insert into cycle (cyc_start) values('" + date + "')");
        return 1;
    }

    public int insertMood(int value) {
        // statement.executeUpdate("Befehl für datenbank"); Ansprechzeile
        this.dbUtil.update("insert into c_mood (******) values('" + value + "')");
        return 1;
    }

    public int insertSlim(int value) {
        // statement.executeUpdate("Befehl für datenbank"); Ansprechzeile
        this.dbUtil.update("insert into c_slim (*********) values('" + value + "')");
        return 1;
    }

    public int insertTemperatura(double value) {
        // statement.executeUpdate("Befehl für datenbank"); Ansprechzeile
        this.dbUtil.update("insert into cycle (cyc_start) values('" + value + "')");
        return 1;
    }

    public void deleteCycle() {
        this.dbUtil.update("delete from cycle");

    }



    public ResultSet getCycles() {
        return this.dbUtil.query(this.SQL_STATS + """
        select cyc_id, date(cyc_start) as cyc_date_start
        from cycle
        """);
    }

    public ResultSet getAvg() {
        return this.dbUtil.query(this.SQL_STATS + """
        select cycle_avg_days 
        from cycle_avg
        """);
    }

    public ResultSet getDiff() {
        return this.dbUtil.query(this.SQL_STATS + """
        select this_cycle, last_cycle, cycle_length as cycle_avg_days 
        from diff
        """);
    }




}




