package com.glofox.unitTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glofox.bookings.controllers.BookingsController;
import com.glofox.bookings.interfaces.IBookingService;
import com.glofox.bookings.model.BookingObject;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingsController.class)
class BookingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IBookingService iBookingService;

	@Test
	public void ifServiceReturnExceptionWhenYouBookADateShouldReturnBadRequest() throws Exception {
		BookingObject bookingObjectRequest = createBookingsObject("test", LocalDate.parse("2024-05-20"));

		given(this.iBookingService.bookDateService(any()))
				.willThrow(new BadRequestException());

		mockMvc.perform(post("/bookings/bookDate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(bookingObjectRequest)))
						.andExpect(status().isBadRequest());
	}

	@Test
	public void ifServiceReturnExceptionWhenYouGetBookingsShouldReturnNotFound() throws Exception {
		given(this.iBookingService.checkBookingsService())
				.willThrow(new IOException());

		mockMvc.perform(get("/bookings/checkBookings"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void ifServiceReturnExceptionWhenYouDeleteBookingShouldReturnNotFound() throws Exception {
		doThrow(new IOException()).when(iBookingService).deleteBookingService(any());

		mockMvc.perform(delete("/bookings/deleteBooking/{id}", UUID.randomUUID()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void ifServiceReturnExceptionWhenYouUpdateBookingShouldReturnNotFound() throws Exception {
		BookingObject bookingObjectRequest = createBookingsObject("test", LocalDate.parse("2024-05-20"));

		doThrow(new IOException()).when(iBookingService).updateBookingService(any(), any());

		mockMvc.perform(put("/bookings/updateBooking/{id}", UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(bookingObjectRequest)))
				.andExpect(status().isNotFound());
	}

	private BookingObject createBookingsObject(String name, LocalDate date){
		BookingObject bookingObject = new BookingObject();
		bookingObject.setName(name);
		bookingObject.setBookingDate(date);
		return bookingObject;
	}
}
