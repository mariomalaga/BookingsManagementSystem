package com.glofox.unitTests;

import com.glofox.bookings.model.BookingObject;
import com.glofox.bookings.services.BookingValidations;
import com.glofox.classes.interfaces.IClassesOperations;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookingValidationsTests {

    @Mock
    private IClassesOperations iClassesOperations;

    private final BookingValidations bookingValidations;

    public BookingValidationsTests(){
        bookingValidations = new BookingValidations(iClassesOperations);
    }

    @Test
    public void ifNameIsNullShouldReturnBadRequestException() throws IOException {

        BookingObject bookingObjectRequest = createBookingsObject(null, LocalDate.parse("2024-05-20"));

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                bookingValidations.validateRequestBookingObject(bookingObjectRequest));

        assertTrue(exception.getMessage().contains("Name is missing or empty"));
    }

    @Test
    public void ifNameIsBlankShouldReturnBadRequestException() throws IOException {

        BookingObject bookingObjectRequest = createBookingsObject(" ", LocalDate.parse("2024-05-20"));

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                bookingValidations.validateRequestBookingObject(bookingObjectRequest));

        assertTrue(exception.getMessage().contains("Name is missing or empty"));
    }

    @Test
    public void ifNameContainsNumbersShouldReturnBadRequestException() throws IOException {

        BookingObject bookingObjectRequest = createBookingsObject("Test1", LocalDate.parse("2024-05-20"));

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                bookingValidations.validateRequestBookingObject(bookingObjectRequest));

        assertTrue(exception.getMessage().contains("Name can not contain numbers"));
    }

    @Test
    public void ifBookingDateIsNullShouldReturnBadRequestException() throws IOException {

        BookingObject bookingObjectRequest = createBookingsObject("Test", null);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                bookingValidations.validateRequestBookingObject(bookingObjectRequest));

        assertTrue(exception.getMessage().contains("Booking date is missing"));
    }

    private BookingObject createBookingsObject(String name, LocalDate date){
        BookingObject bookingObject = new BookingObject();
        bookingObject.setName(name);
        bookingObject.setBookingDate(date);
        return bookingObject;
    }
}
