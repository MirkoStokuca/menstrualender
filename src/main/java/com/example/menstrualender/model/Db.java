package com.example.menstrualender.model;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class Db {
    Connection connection;
    Statement statement;

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
            this.setupCycleDate();
            this.setupTemperatur();
        }
    }

    /*
    Macht eine neue Tabelle Cycle
     */
    public void setupCycleDate() {
        // """ drei macht es zusammenhängend ohne +
        // tabelle erstellen
        // Tabelle braucht eine id "PRIMARY KEY", die soll automatisch erstellt werden:"AUTOINCREMENT"
        // " " hier kann der name für eine neue Spalte erstellt werden, Datentyp, und andere Eigenschaften
        this.update("""
            CREATE TABLE "cycle" (                      
            "cyc_id"	INTEGER NOT NULL,
            "cyc_start"	TEXT NOT NULL UNIQUE,
            "last_cycle" TEXT,
            "cycle_length" INTEGER,
            "cycle_avg_days" INTEGER,
            PRIMARY KEY("cyc_id" AUTOINCREMENT)
            );"""
        );
    }

    public void setupTemperatur() {
        this.update("""
            CREATE TABLE "c_temperatur" (
            "t_id" INTEGER NOT NULL,
            "cyc_id" INTEGER NOT NULL,
            "temperature" INTEGER NOT NULL,
            PRIMARY KEY("t_id" AUTOINCREMENT)
            );"""
        );
    }


    public int insertCycle(LocalDate date) {
        // statement.executeUpdate("Befehl für datenbank"); Ansprechzeile
        this.update("insert into cycle (cyc_start) values('" + date + "')");
        return 1;
    }

    public void deleteCycle() {
        this.update("delete from cycle");

    }

    private void update(String sql) {
        try {
            this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet query(String sql) {
        try {
            return this.statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getCycles() {
        return this.query("""
        select cyc_id, date(cyc_start) as cyc_date_start
        from cycle
        """);
    }

    public ResultSet getAvg() {
        return this.query(this.SQL_STATS + """
        select cycle_avg as cycle_avg_days
        from cycle
        """);
    }

    public ResultSet getDiff() {
        return this.query(this.SQL_STATS + """
        select this_cycle, last_cycle, cycle_length as cycle_avg_days 
        from diff
        """);
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

    // Brauchen wir vielleicht nicht einmal
    private void closeConnection() {
        try
        {
            if(this.connection != null)
                this.connection.close();
        }
        catch(SQLException e)
        {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }
}




