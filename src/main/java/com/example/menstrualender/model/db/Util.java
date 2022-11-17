package com.example.menstrualender.model.db;

import java.io.File;
import java.sql.*;

public class Util {
    //deklaration
    Statement statement;
    Connection connection;


    /**
     * checks if Db Exists. returns boolean
     * @return
     */
    public static boolean isDbExisting(){
        File f = new File("sample.sqlite");
        if (!f.exists()) {          // wenn es die Datenbank noch nicht gibt --> setup
            // noch keine Datenvorhanden, macht Datenbank mit Tabelle verfügbar.
            System.out.println("Noch keine Datenvorhanden!");
            return false;
        }
        else return true;
    }

    /**
     * if Db doesnt exist yet it creates a new one
     * @param password
     * @return
     */
    public boolean setUp(String password){
        boolean isDbExisting = Util.isDbExisting();
        boolean succes = this.getConnection(password);
        if(!isDbExisting) {
            Setup setup = new Setup(this);
            setup.createTables();
            setup.insertData();
        }
        return succes;
    }

    /**
     * creates dataBase connection and handles password
     * @param password
     * @return
     */
    private boolean getConnection(String password) {
        try {
            // create a database connection
            // todo: set password as third argument, to encrypt this database
            // for developing us empty password to easy inspect data with sqlite browser.
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
            return false;
        }
        return true;
    }

    /**
     * calls method to update Data
     * @param sql
     */
    public void update(String sql) {
        try {
            this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param sql
     * @return
     */
    public ResultSet query(String sql) {
        try {
            return this.statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Brauchen wir vielleicht nicht einmal

    /**
     * cloeses the connection
     */
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
