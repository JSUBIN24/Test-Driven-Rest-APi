package com.subin.test.driven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subin.test.driven.model.entity.StudentEntity;
import com.subin.test.driven.model.dto.StudentDTO;
import com.subin.test.driven.repository.StudentRepository;
import com.subin.test.driven.service.StudentService;
import com.subin.test.driven.util.ApiResponse;
import com.subin.test.driven.util.ResponseEntityUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @MockBean
    StudentRepository studentRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddStudentAndReturn201() throws Exception {

        StudentEntity response = new StudentEntity(1L,"Subin Jerin","George", "Male", "subinbenat@gmail.com","8098493258",27, new Date(),new Date());
        StudentDTO request = new StudentDTO("Subin Jerin","George", "Male", "subinbenat@gmail.com","8098493258",27, new Date(),new Date());
        ApiResponse apiResponse = new ApiResponse("Successfully Added Employee",response, HttpStatus.CREATED);
        Mockito.when(studentService.addStudent(request)).thenReturn(apiResponse);

        mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldAddStudentAndReturn500() throws Exception {
        StudentDTO request = new StudentDTO("Subin Jerin","George", "Male", "subinbenat@gmail.com","8098493258",27, new Date(),new Date());
        ApiResponse apiResponse = new ApiResponse("An error occurred while adding the employee ", null,HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.when(studentService.addStudent(any(StudentDTO.class))).thenReturn(apiResponse);
        mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }



    @Test
    void shouldNotAddStudentAndReturn400_IfInvalid() throws Exception {
        //Provided Invalid Request so the validation will get failed and return Bad Request
        StudentDTO request = new StudentDTO("","", "Male", "subinbenat.com","809258",27, new Date(),new Date());
        mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAllStudentAndReturn200() throws Exception{
        List<StudentEntity> studentEntities = List.of(new StudentEntity(1L, "Subin Jerin", "George", "Male", "subinbenat@gmail.com", "8098493258", 27, new Date(), new Date()));
        Mockito.when(studentRepository.findAll()).thenReturn(studentEntities);
        ApiResponse apiResponse = ResponseEntityUtil.populateApiResponse("Student record retrieved successfully", studentEntities, HttpStatus.OK);
        Mockito.when(studentService.getAllStudent()).thenReturn(apiResponse);
        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());
    }
/*
    @Test
    void shouldReturnStudentDetailsById() throws Exception {
        mockMvc.perform()
    }*/
}