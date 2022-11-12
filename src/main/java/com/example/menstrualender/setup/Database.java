package com.example.menstrualender.setup;

import com.example.menstrualender.model.Db;
import com.example.menstrualender.util.DatabaseUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    Connection connection;
    Statement statement;
    DatabaseUtil dbUtil = new DatabaseUtil();

    public Database() {
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
        // Temperature Table erstellen, abhängig von Cycle (cyc_id)
        // toDo: temperatur = double...
        this.dbUtil.update("""
            CREATE TABLE "c_temperature" (
            "t_id" INTEGER NOT NULL,
            "cyc_id" INTEGER NOT NULL,
            "temperature_value" INTEGER,
            PRIMARY KEY ("t_id" AUTOINCREMENT)
            );"""
        );
        // Mood Table erstellen, abhängig von Cycle (cyc_id)
        this.dbUtil.update("""
            CREATE TABLE "c_mood" (
            "m_id" INTEGER NOT NULL,
            "cyc_id" INTEGER NOT NULL,
            "mood" INTEGER,
            PRIMARY KEY ("m_id" AUTOINCREMENT)
            );"""
        );
        // Slim Table erstellen, abhängig von Cycle (cyc_id)
        this.dbUtil.update("""
            CREATE TABLE "c_slim" (
            "s_id" INTEGER NOT NULL,
            "cyc_id" INTEGER NOT NULL,
            "slim" INTEGER,
            PRIMARY KEY ("s_id" AUTOINCREMENT)
            );"""
        );
        // Kommentar Table erstellen, abhängig von Cycle (cyc_id)
        this.dbUtil.update("""
            CREATE TABLE "c_comment" (
            "com_id" INTEGER NOT NULL,
            "cyc_id" INTEGER NOT NULL,
            "comment" TEXT,
            PRIMARY KEY ("com_id" AUTOINCREMENT)
            );"""
        );
        // Blutungsstärke Table erstellen, abhängig von Cycle (cyc_id)
        this.dbUtil.update("""
            CREATE TABLE "c_blutung" (
            "b_id" INTEGER NOT NULL,
            "cyc_id" INTEGER NOT NULL,
            "blutung" TEXT,
            PRIMARY KEY ("b_id" AUTOINCREMENT)
            );"""
        );
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
