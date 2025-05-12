package com.subin.test.driven.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.subin.test.driven.model.entity.EmployeeEntity;
import com.subin.test.driven.exception.EmployeeNotFoundException;
import com.subin.test.driven.model.dto.EmployeeDTO;
import com.subin.test.driven.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    EmployeeRepository employeeRepository;

    @Autowired
    ObjectMapper objectMapper;

    List<EmployeeEntity> employeeEntityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employeeEntityList = List.of(
                new EmployeeEntity(1L,"subin", "GVMS", 55000, "permanent"),
                new EmployeeEntity(2L,"swathi", "IOTA", 35000, "permanent")
        );
    }

    @Test
    void shouldReturnTheUser_Exists() throws Exception {
        Long empId = 1L;
        Mockito.when(employeeRepository.findById(empId)).thenReturn(Optional.of(employeeEntityList.get(0)));
        mockMvc.perform(get("/api/v1/employee/{empId}", empId))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotFindTheEmployee_NotExists() throws Exception {
        Long empId = 10L;
        Mockito.when(employeeRepository.findById(empId)).thenThrow(EmployeeNotFoundException.class);
        mockMvc.perform(get("/api/v1/employee/{empId}", empId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSaveEmployee_WhenEmployeeIsValid() throws Exception{
        EmployeeDTO employeeDTO = new EmployeeDTO(5L,"subin","GVMS",55000,"permanent");
        Mockito.when(employeeRepository.save(employeeEntityList.get(0))).thenReturn(employeeEntityList.get(0));
        mockMvc.perform(post("/api/v1/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotSaveEmployee_WhenEmployeeIsNotValid() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(null,"","GVMS",55000,"permanent");
        Mockito.when(employeeRepository.save(employeeEntityList.get(0))).thenReturn(employeeEntityList.get(0));
        mockMvc.perform(post("/api/v1/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shoutUpdateEmployee_IfExist() throws Exception{
        EmployeeDTO employeeDTO = new EmployeeDTO(1L,"Jerin","GVMS",55000,"permanent");
        EmployeeEntity mockedEmployee = new EmployeeEntity(1L, "Subin", "GVMS", 55000, "permanent");
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockedEmployee));
        mockMvc.perform(put("/api/v1/employee/{empId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO))

                )
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistEmployee_whenUpdate_thenThrowException() throws Exception{
        EmployeeDTO employeeDTO = new EmployeeDTO(10L,"subin","GVMS",55000,"permanent");
        mockMvc.perform(put("/api/v1/employee/{empId}",employeeDTO.empId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO))
        )
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldDeleteEmployee_IfExist() throws Exception{
        EmployeeEntity mockedEmployee = new EmployeeEntity(1L, "Subin", "GVMS", 55000, "permanent");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockedEmployee));
        doNothing().when(employeeRepository).deleteById(anyLong());
        mockMvc.perform(delete("/api/v1/employee/{empId}",1L)) //anyLong() is a Mockito matcher, not a real value. mockMvc.perform(...) expects a concrete value, not a matcher.
                .andExpect(status().isNoContent());
//        verify(employeeRepository,times(1)).deleteById(anyLong());
//        verify(employeeRepository,times(1)).findById(anyLong());
    }

    @Test
    void givenNonExistingEmployee_whenDelete_thenThrowException() throws Exception {
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(employeeRepository).deleteById(anyLong());
        mockMvc.perform(delete("/api/v1/employee/{empId}",15L))
                .andExpect(status().isNotFound());
    }

}
