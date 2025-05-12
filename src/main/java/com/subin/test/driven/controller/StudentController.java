package com.subin.test.driven.controller;

import com.subin.test.driven.model.dto.StudentDTO;
import com.subin.test.driven.service.implementation.StudentService;
import com.subin.test.driven.util.ApiResponse;
import com.subin.test.driven.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("student")
    public ResponseEntity<ApiResponse> addStudent (@Valid @RequestBody StudentDTO studentDTO){
        ApiResponse apiResponse = studentService.addStudent(studentDTO);
        return ResponseEntityUtil.build(apiResponse);
    }

    @GetMapping("students")
    public ResponseEntity<ApiResponse> getAllStudent(){
        ApiResponse apiResponse = studentService.getAllStudent();
        return ResponseEntityUtil.build(apiResponse);
    }

    @ GetMapping("student/{studentId}")
    public ResponseEntity<ApiResponse> getStudent(@PathVariable Long studentId){
        ApiResponse apiResponse = studentService.getStudent(studentId);
        return ResponseEntityUtil.build(apiResponse);
    }

    @PutMapping("student")
    public ResponseEntity<ApiResponse> updateStudent(@RequestBody Long studentId){
        ApiResponse apiResponse = studentService.updateStudent(studentId);
        return ResponseEntityUtil.build(apiResponse);
    }





}
