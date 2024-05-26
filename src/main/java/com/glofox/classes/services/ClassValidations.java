package com.glofox.classes.services;

import com.glofox.classes.interfaces.IClassValidations;
import com.glofox.classes.interfaces.IClassesOperations;
import com.glofox.classes.models.ClassObject;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class ClassValidations implements IClassValidations {
    private final IClassesOperations iClassesOperations;

    public ClassValidations(IClassesOperations iClassesOperations) {
        this.iClassesOperations = iClassesOperations;
    }

    public void validateRequestClassObjectCreate(ClassObject classObject) throws IOException {
        validateRequestClassObject(classObject);
        checkIfDateAvailableCreate(classObject.getStart_date());
        checkIfDateAvailableCreate(classObject.getEnd_date());
    }

    public void validateRequestClassObjectUpdate(ClassObject classObject, UUID id) throws IOException {
        validateRequestClassObject(classObject);
        checkIfDateAvailableUpdate(classObject.getStart_date(), id);
        checkIfDateAvailableUpdate(classObject.getEnd_date(), id);
    }

    private void validateRequestClassObject(ClassObject classObject) throws IOException {
        checkIfClassNameIsValid(classObject.getClassName());
        checkStartDateIsNull(classObject.getStart_date());
        checkEndDateIsNull(classObject.getEnd_date());
        checkIfCapacityIsValid(classObject.getCapacity());
        checkDateFormat(classObject.getStart_date());
        checkDateFormat(classObject.getEnd_date());
        checkIfStartDateIsGreaterOrEqualThanEndDate(classObject.getStart_date(), classObject.getEnd_date());
    }

    private void checkIfClassNameIsValid(String className) throws BadRequestException {
        if (className == null || className.isBlank()) {
            throw new BadRequestException("Class Name is missing or empty");
        }
        else if(checkStringContainsNumbers(className)){
            throw new BadRequestException("Class Name can not contain numbers");
        }
    }

    private void checkStartDateIsNull(LocalDate startDate) throws BadRequestException {
        if (startDate == null) {
            throw new BadRequestException("Start date is missing");
        }
    }

    private void checkEndDateIsNull(LocalDate endDate) throws BadRequestException {
        if (endDate == null) {
            throw new BadRequestException("End date is missing");
        }
    }

    private void checkIfCapacityIsValid(Integer capacity) throws BadRequestException {
        if (capacity == null || capacity <= 0) {
            throw new BadRequestException("Capacity is missing or is not valid");
        }
    }

    private void checkIfStartDateIsGreaterOrEqualThanEndDate (LocalDate startDate, LocalDate endDate) throws BadRequestException {

        if (ChronoUnit.DAYS.between(startDate, endDate) < 0)
        {
            throw new BadRequestException("End Date needs to be greater than Start Date");
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

    private void checkIfDateAvailableCreate(LocalDate date) throws IOException {
        List<ClassObject> classes = iClassesOperations.checkClasses();
        for (ClassObject classObject : classes){
            if((date.isEqual(classObject.getStart_date()) || date.isAfter(classObject.getStart_date())) &&
                    (date.isEqual(classObject.getEnd_date()) || date.isBefore(classObject.getEnd_date()))){
                throw new BadRequestException("The date " + date + " is not available");
            }
        }
    }

    private void checkIfDateAvailableUpdate(LocalDate date, UUID id) throws IOException {
        List<ClassObject> classes = iClassesOperations.checkClasses();
        for (ClassObject classObject : classes){
            if((date.isEqual(classObject.getStart_date()) || date.isAfter(classObject.getStart_date())) &&
                    (date.isEqual(classObject.getEnd_date()) || date.isBefore(classObject.getEnd_date()))){
                if(!id.toString().equals(classObject.getId().toString())){
                    throw new BadRequestException("The date " + date + " is not available");
                }
            }
        }
    }
}
