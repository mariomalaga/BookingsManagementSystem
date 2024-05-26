package com.glofox.bookings.interfaces;

import com.glofox.bookings.model.BookingObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IBookingService {
    BookingObject bookDateService(BookingObject bookingObject) throws IOException;
    List<BookingObject> checkBookingsService() throws IOException;
    void deleteBookingService(UUID id) throws IOException;
    void updateBookingService(UUID id, BookingObject bookingObject) throws IOException;
}
