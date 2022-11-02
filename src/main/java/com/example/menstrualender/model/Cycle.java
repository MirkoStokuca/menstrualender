package com.example.menstrualender.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class Cycle {

    private final ObjectProperty<LocalDate> cycleStart;

    /**
     * Default constructor
     */
    public Cycle (){
        this(null);
    }

    public Cycle (SimpleObjectProperty<LocalDate> startDate){
        this.cycleStart = startDate;
    }

    public LocalDate getCycleStart() {
        return cycleStart.get();
    }

    public ObjectProperty<LocalDate> cycleStartProperty() {
        return cycleStart;
    }

    public void setCycleStart(LocalDate cycleStart) {
        this.cycleStart.set(cycleStart);
    }
}
