package com.glofox.classes.controllers;

import com.glofox.classes.interfaces.IClassService;
import com.glofox.classes.models.ClassObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classes")
public class ClassesController {
    private final IClassService iClassService;

    public ClassesController(IClassService iClassService)
    {
        this.iClassService = iClassService;
    }

    @PostMapping("/addClass")
    public ClassObject bookClass(@RequestBody ClassObject classObject) throws IOException {
        return iClassService.bookClassService(classObject);
    }

    @GetMapping("/checkClasses")
    public List<ClassObject> checkClasses() throws IOException {
        return iClassService.checkClassesService();
    }

    @DeleteMapping("/deleteClass/{id}")
    public void deleteClass(@PathVariable UUID id) throws IOException {
        iClassService.deleteClassService(id);
    }

    @PutMapping("/updateClass/{id}")
    public ClassObject updateClass(@PathVariable UUID id, @RequestBody ClassObject classObject) throws IOException {
        iClassService.updateClassService(id, classObject);
        return classObject;
    }
}
