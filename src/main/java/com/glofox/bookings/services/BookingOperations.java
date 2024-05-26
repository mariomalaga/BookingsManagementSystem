package com.glofox.bookings.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glofox.bookings.interfaces.IBookingOperations;
import com.glofox.bookings.model.BookingObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingOperations implements IBookingOperations {
    private static final String FILE_PATH = "bookings.txt";
    private final ObjectMapper objectMapper;

    public BookingOperations()
    {
        objectMapper = new ObjectMapper();
        changeObjectMapperToSupportLocalDate();
    }

    public void saveBooking(BookingObject booking) throws IOException {
        FileWriter writer = new FileWriter(FILE_PATH, true);
        String stringBooking = objectMapper.writeValueAsString(booking);
        writer.write(stringBooking);
        writer.write(System.lineSeparator()); // Add a new line after each entry
        writer.close();
    }

    public List<BookingObject> checkBookings() throws IOException {
        return getBookingObjectList();
    }

    public void deleteBooking(UUID id) throws IOException {
        List<BookingObject> bookingObjects = getBookingObjectList();
        new PrintWriter(FILE_PATH).close();
        FileWriter writer = new FileWriter(FILE_PATH, true);
        bookingObjects.forEach(booking -> {
            try {
                if(!booking.getId().toString().equals(id.toString())) {
                    String stringBooking = objectMapper.writeValueAsString(booking);
                    writer.write(stringBooking);
                    writer.write(System.lineSeparator());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    public void updateBooking(UUID id, BookingObject bookingObject) throws IOException {
        List<BookingObject> bookingObjects = getBookingObjectList();
        new PrintWriter(FILE_PATH).close();
        FileWriter writer = new FileWriter(FILE_PATH, true);
        bookingObjects.forEach(booking -> {
            try {
                if(booking.getId().toString().equals(id.toString())) {
                    booking.setName(bookingObject.getName());
                    booking.setBookingDate(bookingObject.getBookingDate());
                }
                String stringBooking = objectMapper.writeValueAsString(booking);
                writer.write(stringBooking);
                writer.write(System.lineSeparator());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    private List<BookingObject> getBookingObjectList() throws IOException {
        List<String> bookings = getAllBookings();
        List<BookingObject> bookingObjects = new ArrayList<>();
        bookings.forEach(booking -> {
            try {
                bookingObjects.add(objectMapper.readValue(booking, BookingObject.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return bookingObjects;
    }

    private List<String> getAllBookings() throws IOException {
        Path path = Paths.get(FILE_PATH);
        return Files.readAllLines(path);
    }

    private void changeObjectMapperToSupportLocalDate(){
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
