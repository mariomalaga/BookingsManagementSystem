package com.glofox.bookings.interfaces;

import com.glofox.bookings.model.BookingObject;

import java.io.IOException;

public interface IBookingValidations {
    void validateRequestBookingObject(BookingObject bookingObject) throws IOException;
}
