package com.glofox.bookings.interfaces;

import com.glofox.bookings.model.BookingObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IBookingOperations {
    void saveBooking(BookingObject booking) throws IOException;
    List<BookingObject> checkBookings() throws IOException;
    void deleteBooking(UUID id) throws IOException;
    void updateBooking(UUID id, BookingObject bookingObject) throws IOException;
}
