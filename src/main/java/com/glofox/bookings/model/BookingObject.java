package com.glofox.bookings.model;

import java.time.LocalDate;
import java.util.UUID;

public class BookingObject {

    private UUID id;

    private String name;

    private LocalDate bookingDate;

    public BookingObject()
    {

    }

    public BookingObject(String name, LocalDate bookingDate) {
        id = UUID.randomUUID();
        this.name = name;
        this.bookingDate = bookingDate;
    }

    public UUID getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
