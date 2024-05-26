package com.glofox.classes.interfaces;

import com.glofox.classes.models.ClassObject;

import java.io.IOException;
import java.util.UUID;

public interface IClassValidations {
    void validateRequestClassObjectUpdate(ClassObject classObject, UUID id) throws IOException;
    void validateRequestClassObjectCreate(ClassObject classObject) throws IOException;
}
