package com.glofox.classes.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glofox.classes.interfaces.IClassesOperations;
import com.glofox.classes.models.ClassObject;
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
public class ClassesOperations implements IClassesOperations {
    private static final String FILE_PATH = "classes.txt";
    private final ObjectMapper objectMapper;

    public ClassesOperations()
    {
        objectMapper = new ObjectMapper();
        changeObjectMapperToSupportLocalDate();
    }

    public void saveClass(ClassObject classObject) throws IOException {
        FileWriter writer = new FileWriter(FILE_PATH, true);
        String stringClass = objectMapper.writeValueAsString(classObject);
        writer.write(stringClass);
        writer.write(System.lineSeparator()); // Add a new line after each entry
        writer.close();
    }

    public List<ClassObject> checkClasses() throws IOException {
        return getClassesObjectList();
    }

    public void deleteClass(UUID id) throws IOException {
        List<ClassObject> classObjects = getClassesObjectList();
        new PrintWriter(FILE_PATH).close();
        FileWriter writer = new FileWriter(FILE_PATH, true);
        classObjects.forEach(classObject -> {
            try {
                if(!classObject.getId().toString().equals(id.toString())) {
                    String stringClass = objectMapper.writeValueAsString(classObject);
                    writer.write(stringClass);
                    writer.write(System.lineSeparator());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    public void updateClass(UUID id, ClassObject classObjectRequest) throws IOException {
        List<ClassObject> classObjects = getClassesObjectList();
        new PrintWriter(FILE_PATH).close();
        FileWriter writer = new FileWriter(FILE_PATH, true);
        classObjects.forEach(classObject -> {
            try {
                if(classObject.getId().toString().equals(id.toString())) {
                    classObject.setClassName(classObjectRequest.getClassName());
                    classObject.setStart_date(classObjectRequest.getStart_date());
                    classObject.setEnd_date(classObjectRequest.getEnd_date());
                    classObject.setCapacity(classObjectRequest.getCapacity());
                }
                String stringClass = objectMapper.writeValueAsString(classObject);
                writer.write(stringClass);
                writer.write(System.lineSeparator());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    private List<ClassObject> getClassesObjectList() throws IOException {
        List<String> classes = getAllClasses();
        List<ClassObject> classesObjects = new ArrayList<>();
        classes.forEach(booking -> {
            try {
                classesObjects.add(objectMapper.readValue(booking, ClassObject.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return classesObjects;
    }

    private List<String> getAllClasses() throws IOException {
        Path path = Paths.get(FILE_PATH);
        return Files.readAllLines(path);
    }

    private void changeObjectMapperToSupportLocalDate(){
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
