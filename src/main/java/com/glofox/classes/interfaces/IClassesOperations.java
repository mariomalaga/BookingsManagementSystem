package com.glofox.classes.interfaces;

import com.glofox.classes.models.ClassObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IClassesOperations {
    void saveClass(ClassObject classObject) throws IOException;
    List<ClassObject> checkClasses() throws IOException;
    void deleteClass(UUID id) throws IOException;
    void updateClass(UUID id, ClassObject classObjectRequest) throws IOException;
}
