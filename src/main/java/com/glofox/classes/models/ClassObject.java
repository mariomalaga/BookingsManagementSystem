package com.glofox.classes.models;

import java.time.LocalDate;
import java.util.UUID;

public class ClassObject {
    private UUID id;

    private String className;

    private LocalDate start_date;

    private LocalDate end_date;

    private Integer capacity;

    public ClassObject()
    {

    }

    public ClassObject(String className, LocalDate start_date, LocalDate end_date, Integer capacity)
    {
        id = UUID.randomUUID();
        this.className = className;
        this.start_date = start_date;
        this.end_date = end_date;
        this.capacity = capacity;
    }

    public UUID getId(){
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
