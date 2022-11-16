package com.example.menstrualender.model.db;

public class Setup {
    Util util;

    public Setup(Util util) {
        this.util = util;
    }

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
        // toDo: temperatur = double...
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
            "vulation" INTEGER,
            PRIMARY KEY ("ovu_id" AUTOINCREMENT)
            );"""
        );
    }
}
