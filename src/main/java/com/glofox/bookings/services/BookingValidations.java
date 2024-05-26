package com.glofox.bookings.services;

import com.glofox.bookings.interfaces.IBookingValidations;
import com.glofox.bookings.model.BookingObject;
import com.glofox.classes.interfaces.IClassesOperations;
import com.glofox.classes.models.ClassObject;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class BookingValidations implements IBookingValidations {
    private final IClassesOperations iClassesOperations;

    public BookingValidations(IClassesOperations iClassesOperations) {
        this.iClassesOperations = iClassesOperations;
    }

    public void validateRequestBookingObject(BookingObject bookingObject) throws IOException {
        checkNameIsValid(bookingObject.getName());
        checkBookingDateIsNull(bookingObject.getBookingDate());
        checkDateFormat(bookingObject.getBookingDate());
        checkIfThereIsAClass(bookingObject.getBookingDate());
    }

    private void checkNameIsValid(String name) throws BadRequestException {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Name is missing or empty");
        }
        else if(checkStringContainsNumbers(name)){
            throw new BadRequestException("Name can not contain numbers");
        }
    }

    public void checkBookingDateIsNull(LocalDate bookingDate) throws BadRequestException {
        if (bookingDate == null) {
            throw new BadRequestException("Booking date is missing");
        }
    }

    private static boolean checkStringContainsNumbers(String input) {
        return input.matches(".*\\d.*");
    }

    private void checkDateFormat(LocalDate date) throws DateTimeParseException {
        String dateString = date.toString();
        String dateFormat = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        formatter.parse(dateString);
    }

    public void checkIfThereIsAClass(LocalDate date) throws IOException {
        List<ClassObject> classes = iClassesOperations.checkClasses();
        boolean checkClassAvailability = false;
        for (ClassObject classObject : classes){
            if((date.isEqual(classObject.getStart_date()) || date.isAfter(classObject.getStart_date())) &&
                    (date.isEqual(classObject.getEnd_date()) || date.isBefore(classObject.getEnd_date()))) {
                checkClassAvailability = true;
            }
        }
        if(!checkClassAvailability){
            throw new BadRequestException("There is no class for: " + date);
        }
    }
}
