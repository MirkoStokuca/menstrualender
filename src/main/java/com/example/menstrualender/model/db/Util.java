package com.example.menstrualender.model.db;

import java.io.File;
import java.sql.*;

public class Util {
    Statement statement;
    Connection connection;

    public Util(){
        boolean noSetFile = false;
        File f = new File("sample.sqlite");
        if (!f.exists()) {          // wenn es die Datenbank noch nicht gibt --> setup
            // noch keine Datenvorhanden, macht Datenbank mit Tabelle verfügbar.
            System.out.println("noch keine Datenvorhanden, macht Datenbank mit Tabelle verfügbar.");
            noSetFile = true;
        }
        this.getConnection();
        if(noSetFile) {
            Setup setup = new Setup(this);
            setup.createTables();
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

    public void update(String sql) {
        try {
            this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet query(String sql) {
        try {
            return this.statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
