package com.glofox.classes.services;

import com.glofox.classes.interfaces.IClassService;
import com.glofox.classes.interfaces.IClassValidations;
import com.glofox.classes.interfaces.IClassesOperations;
import com.glofox.classes.models.ClassObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ClassService implements IClassService {
    private final IClassesOperations iClassesOperations;
    private final IClassValidations iClassValidations;

    public ClassService(IClassesOperations iClassesOperations, IClassValidations iClassValidations) {
        this.iClassesOperations = iClassesOperations;
        this.iClassValidations = iClassValidations;
    }

    public ClassObject bookClassService(ClassObject classObject) throws IOException {
        iClassValidations.validateRequestClassObjectCreate(classObject);
        ClassObject responseClass = new ClassObject(classObject.getClassName(), classObject.getStart_date(), classObject.getEnd_date(), classObject.getCapacity());
        iClassesOperations.saveClass(responseClass);
        return responseClass;
    }

    public List<ClassObject> checkClassesService() throws IOException {
        return iClassesOperations.checkClasses();
    }

    public void deleteClassService(UUID id) throws IOException {
        iClassesOperations.deleteClass(id);
    }

    public void updateClassService(UUID id, ClassObject classObject) throws IOException {
        iClassValidations.validateRequestClassObjectUpdate(classObject, id);
        iClassesOperations.updateClass(id, classObject);
    }
}
