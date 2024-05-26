package com.glofox.bookings.controllers;

import com.glofox.bookings.interfaces.IBookingService;
import com.glofox.bookings.model.BookingObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    private final IBookingService iBookingService;

    public BookingsController(IBookingService iBookingService)
    {
        this.iBookingService = iBookingService;
    }

    @PostMapping("/bookDate")
    public BookingObject bookDate(@RequestBody BookingObject bookingObject) throws IOException {
        return iBookingService.bookDateService(bookingObject);
    }

    @GetMapping("/checkBookings")
    public List<BookingObject> checkBookings() throws IOException {
        return iBookingService.checkBookingsService();
    }

    @DeleteMapping("/deleteBooking/{id}")
    public void deleteBooking(@PathVariable UUID id) throws IOException {
        iBookingService.deleteBookingService(id);
    }

    @PutMapping("/updateBooking/{id}")
    public BookingObject updateBooking(@PathVariable UUID id, @RequestBody BookingObject bookingObject) throws IOException {
        iBookingService.updateBookingService(id, bookingObject);
        return bookingObject;
    }
}
