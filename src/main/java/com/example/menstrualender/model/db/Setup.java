package com.example.menstrualender.model.db;

public class Setup {
    //deklaration
    Util util;

    /**
     * constructor
     */

    public Setup(Util util) {
        this.util = util;
    }

    /**
     * creation of the Tables for DataBase structure
     */
    public void createTables() {
        // """ drei macht es zusammenhängend ohne +, ganz vorne schreiben, damit es nicht unnötig zeichen verbraucht.
        //Cycle tabelle erstellen:
        this.util.update("""
                CREATE TABLE "cycle" (
                "cyc_id"	INTEGER NOT NULL,
                "cyc_start"	TEXT NOT NULL UNIQUE,
                PRIMARY KEY("cyc_id" AUTOINCREMENT)
                );"""
        );
        // Temperature Table erstellen, abhängig von Cycle (cyc_id)
        this.util.update("""
                CREATE TABLE "c_temperature" (
                "t_id" INTEGER NOT NULL,
                "cyc_id" INTEGER NOT NULL,
                "temperature_value" INTEGER,
                PRIMARY KEY ("t_id" AUTOINCREMENT)
                );"""
        );
        // Mood Table erstellen, abhängig von Cycle (cyc_id)
        this.util.update("""
                CREATE TABLE "c_mood" (
                "m_id" INTEGER NOT NULL,
                "cyc_id" INTEGER NOT NULL,
                "mood" INTEGER,
                PRIMARY KEY ("m_id" AUTOINCREMENT)
                );"""
        );
        // Outflow (=Ausfluss / Schleim) Table erstellen, abhängig von Cycle (cyc_id)
        this.util.update("""
                CREATE TABLE "c_outflow" (
                "o_id" INTEGER NOT NULL,
                "cyc_id" INTEGER NOT NULL,
                "outflow" INTEGER,
                PRIMARY KEY ("o_id" AUTOINCREMENT)
                );"""
        );
        // Kommentar Table erstellen, abhängig von Cycle (cyc_id)
        this.util.update("""
                CREATE TABLE "c_comment" (
                "com_id" INTEGER NOT NULL,
                "cyc_id" INTEGER NOT NULL,
                "comment" TEXT,
                PRIMARY KEY ("com_id" AUTOINCREMENT)
                );"""
        );
        // Blutungsstärke Table erstellen, abhängig von Cycle (cyc_id)
        this.util.update("""
                CREATE TABLE "c_bleeding" (
                "b_id" INTEGER NOT NULL,
                "cyc_id" INTEGER NOT NULL,
                "bleeding" TEXT,
                PRIMARY KEY ("b_id" AUTOINCREMENT)
                );"""
        );
        // Ovulation Table erstellen, abhängig von Cycle (cyc_id)
        this.util.update("""
                CREATE TABLE "c_ovulation" (
                "ovu_id" INTEGER NOT NULL,
                "cyc_id" INTEGER NOT NULL,
                "ovulation_date" TEXT,
                PRIMARY KEY ("ovu_id" AUTOINCREMENT)
                );"""
        );
    }

    // insert Data to:
    // - Cycle
    // - Bleeding
    // - Temperature
    public void insertData() {
        int month, id = 1;
        for (month = 1 ; month < 10 ; month++ ){
            this.util.update("""
                insert into cycle (cyc_start)
                values ('2022-0"""+ month + "-01')"
            );
            for (int i = 0 ; i < 10 ; i++ ) {
                this.util.update("""
                    insert into c_temperature (cyc_id, temperature_value)
                    values (""" + id + ", '37.56')"
                );
                this.util.update("""
                    insert into c_temperature (cyc_id, temperature_value)
                    values (""" + id + ", '36.36')"
                );
                this.util.update("""
                        insert into c_outflow (cyc_id, outflow)
                        values (""" + id + ", '1')"
                );
            }
            for (int c = 0 ; c < 5; c++) {
                this.util.update("""
                    insert into c_bleeding (cyc_id, bleeding)
                    values (""" + id + ", '++')"
                );
            }
            id++;
        }
    }
}
