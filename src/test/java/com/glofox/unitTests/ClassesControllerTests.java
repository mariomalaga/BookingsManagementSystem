package com.glofox.unitTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glofox.classes.controllers.ClassesController;
import com.glofox.classes.interfaces.IClassService;
import com.glofox.classes.models.ClassObject;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClassesController.class)
public class ClassesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IClassService iClassService;

    @Test
    public void ifServiceReturnExceptionWhenYouBookAClassShouldReturnBadRequest() throws Exception {
        ClassObject classObjectRequest = createClassObject("test", LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), 10);

        given(this.iClassService.bookClassService(any()))
                .willThrow(new BadRequestException());

        mockMvc.perform(post("/classes/addClass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classObjectRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ifServiceReturnExceptionWhenYouGetClassesShouldReturnNotFound() throws Exception {
        given(this.iClassService.checkClassesService())
                .willThrow(new IOException());

        mockMvc.perform(get("/classes/checkClasses"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void ifServiceReturnExceptionWhenYouDeleteClassShouldReturnNotFound() throws Exception {
        doThrow(new IOException()).when(iClassService).deleteClassService(any());

        mockMvc.perform(delete("/classes/deleteClass/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void ifServiceReturnExceptionWhenYouUpdateClassShouldReturnNotFound() throws Exception {
        ClassObject classObjectRequest = createClassObject("test", LocalDate.parse("2024-05-20"), LocalDate.parse("2024-05-25"), 10);

        doThrow(new IOException()).when(iClassService).updateClassService(any(), any());

        mockMvc.perform(put("/classes/updateClass/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classObjectRequest)))
                .andExpect(status().isNotFound());
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
