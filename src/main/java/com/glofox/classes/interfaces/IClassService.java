package com.glofox.classes.interfaces;

import com.glofox.classes.models.ClassObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IClassService {
    ClassObject bookClassService(ClassObject classObject) throws IOException;
    List<ClassObject> checkClassesService() throws IOException;
    void deleteClassService(UUID id) throws IOException;
    void updateClassService(UUID id, ClassObject classObject) throws IOException;
}
