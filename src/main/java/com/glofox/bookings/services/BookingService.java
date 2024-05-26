package com.glofox.bookings.services;

import com.glofox.bookings.interfaces.IBookingService;
import com.glofox.bookings.interfaces.IBookingOperations;
import com.glofox.bookings.interfaces.IBookingValidations;
import com.glofox.bookings.model.BookingObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService implements IBookingService {
    private final IBookingOperations iBookingOperations;
    private final IBookingValidations iBookingValidations;

    public BookingService(IBookingOperations iBookingOperations, IBookingValidations iBookingValidations) {
        this.iBookingOperations = iBookingOperations;
        this.iBookingValidations = iBookingValidations;
    }

    public BookingObject bookDateService(BookingObject bookingObject) throws IOException {
        iBookingValidations.validateRequestBookingObject(bookingObject);
        BookingObject responseBooking = new BookingObject(bookingObject.getName(), bookingObject.getBookingDate());
        iBookingOperations.saveBooking(responseBooking);
        return responseBooking;
    }

    public List<BookingObject> checkBookingsService() throws IOException {
        return iBookingOperations.checkBookings();
    }

    public void deleteBookingService(UUID id) throws IOException {
        iBookingOperations.deleteBooking(id);
    }

    public void updateBookingService(UUID id, BookingObject bookingObject) throws IOException {
        iBookingValidations.validateRequestBookingObject(bookingObject);
        iBookingOperations.updateBooking(id, bookingObject);
    }
}
