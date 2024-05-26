package com.glofox.unitTests;

import com.glofox.bookings.model.BookingObject;
import com.glofox.bookings.services.BookingValidations;
import com.glofox.classes.interfaces.IClassesOperations;
import com.glofox.classes.models.ClassObject;
import com.glofox.classes.services.ClassValidations;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ClassesValidationsTests {
    @Mock
    private IClassesOperations iClassesOperations;

    private final ClassValidations classValidations;

    public ClassesValidationsTests() {
        classValidations = new ClassValidations(iClassesOperations);
    }

    @Test
    public void ifClassNameIsNullShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject(null, LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), 10);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest));

        assertTrue(exception.getMessage().contains("Class Name is missing or empty"));
    }

    @Test
    public void ifClassNameIsBlankShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject(" ", LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), 10);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("Class Name is missing or empty"));
    }

    @Test
    public void ifClassNameContainsNumbersShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject("Test1", LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), 10);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("Class Name can not contain numbers"));
    }

    @Test
    public void ifStartDateIsNullShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject("Test", null, LocalDate.parse("2024-05-25"), 10);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("Start date is missing"));
    }

    @Test
    public void ifEndDateIsNullShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject("Test", LocalDate.parse("2024-05-20"), null, 10);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("End date is missing"));
    }

    @Test
    public void ifCapacityIsNullShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject("Test", LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), null);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("Capacity is missing or is not valid"));
    }

    @Test
    public void ifCapacityIsNegativeOrZeroShouldReturnBadRequestException() throws IOException {

        ClassObject classObjectRequest = createClassObject("Test", LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), -2);

        BadRequestException exception = assertThrows(BadRequestException.class,() ->
                classValidations.validateRequestClassObject(classObjectRequest, UUID.randomUUID()));

        assertTrue(exception.getMessage().contains("Capacity is missing or is not valid"));
    }

    private ClassObject createClassObject(String className, LocalDate startDate, LocalDate endDate, Integer capacity ){
        ClassObject classObject = new ClassObject();
        classObject.setClassName(className);
        classObject.setStart_date(startDate);
        classObject.setEnd_date(endDate);
        classObject.setCapacity(capacity);
        return classObject;
    }
}
