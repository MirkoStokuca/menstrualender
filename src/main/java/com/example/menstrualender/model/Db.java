package com.example.menstrualender.model;

import com.example.menstrualender.model.db.Util;
import java.sql.*;
import java.time.LocalDate;

public class Db {
    Util util;

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
            """
    ;

    protected String SQL_GET_CYC_ID = """
            select cyc_id
            from cycle
            order by cyc_start desc
            limit 1
            """
    ;

    public Db() {
        this.util = new Util();
    }


    public int insertCycle(LocalDate date) {
        this.util.update("insert into cycle (cyc_start) values('" + date + "')");
        return 1;
    }

    public int insertMood(int value) {
        this.util.update("insert into c_mood (cyc_id, mood) values((" + SQL_GET_CYC_ID + "),'" + value + "')");
        return 1;
    }

    public int insertOutflow(int value) {
        this.util.update("insert into c_outflow (cyc_id, outflow) values((" + SQL_GET_CYC_ID + "),'" + value + "')");
        return 1;
    }

    public int insertTemperature(String value) {
        this.util.update("insert into c_temperature (cyc_id, temperature_value) values((" + SQL_GET_CYC_ID + "),'" + value + "')");
        return 1;
    }

    public int insertComment(String comment) {
        this.util.update("insert into c_comment (cyc_id, comment) values((" + SQL_GET_CYC_ID + "),'" + comment + "')");
        return 1;
    }

    public int insertBleeding(int value) {
        this.util.update("insert into c_bleeding (cyc_id, bleeding) values((" + SQL_GET_CYC_ID + "),'" + value + "')");
        return 1;
    }

    public int insertOvulation(LocalDate date) {
        this.util.update("insert into c_ovulation (cyc_id, ovulation_date) values((" + SQL_GET_CYC_ID + "),'" + date + "')");
        return 1;
    }

    public void deleteCycle() {
        this.util.update("delete from cycle");
    }

    public ResultSet getCycles() {
        return this.util.query(this.SQL_STATS + """
        select cyc_id, date(cyc_start) as cyc_date_start
        from cycle
        """);
    }

    /**
     * History Cycles:
     */
    public ResultSet getCyclesHistoryIntervals() {
        return this.util.query("""
                with base as (
                     select
                         cyc_id
                         , date(cyc_start) as start_cycle
                         , lag(cyc_start) over (order by date(cyc_start)) as last_cycle
                     from cycle
                     order by date(cyc_start) desc
                     limit 12
                 ), length as (
                     select cyc_id
                          , start_cycle
                          , last_cycle
                          , julianday(start_cycle) - julianday(last_cycle) as cycle_length
                     from base
                     where last_cycle is not null
                 ), bleeding_days as (
                    select cyc_id, count(*) as first_interval
                    from c_bleeding
                    group by cyc_id
                 ), interval as (
                     select length.cyc_id
                          , start_cycle
                          , cycle_length
                          , first_interval
                          , round(cycle_length/2 - first_interval -4) as second_interval
                          , cycle_length - first_interval - round(cycle_length/2 - first_interval -4) -7 as fourth_interval
                     from length
                     inner join bleeding_days
                     on bleeding_days.cyc_id = length.cyc_id
                     group by length.cyc_id
                 )
                 select *
                 from interval;
                 """);
    }


    public ResultSet getAvg() {
        return this.util.query(this.SQL_STATS + """
        select cycle_avg_days
        from cycle_avg
        """);
    }
}




