package com.subin.test.driven.service;

import com.subin.test.driven.controller.StudentController;
import com.subin.test.driven.model.entity.StudentEntity;
import com.subin.test.driven.model.dto.StudentDTO;
import com.subin.test.driven.repository.StudentRepository;
import com.subin.test.driven.util.ApiResponse;
import com.subin.test.driven.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.function.Consumer;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static Runnable getStudentDetailsNotFound(Long studentId, ApiResponse[] apiResponses) {
        return () -> {
            log.error("Student details not found for the Id = {}", studentId);
            apiResponses[0] = ResponseEntityUtil.populateApiResponse("Student not found with ID : {}", studentId, HttpStatus.NOT_FOUND); //404
        };
    }

    private static Consumer<StudentEntity> getStudentEntityConsumer(Long studentId, ApiResponse[] apiResponses) {
        return student -> {
            log.info("Student details found for the Id = {}", studentId);
            apiResponses[0] = ResponseEntityUtil.populateApiResponse("Student found", student, HttpStatus.OK);  //200
        };
    }

    private static Runnable getDetailsNotFound(Long studentId, ApiResponse[] apiResponses) {
        return getStudentDetailsNotFound(studentId, apiResponses);
    }

    private static StudentEntity populateStudentEntity(StudentDTO studentDTO) {
        StudentEntity student = new StudentEntity();
        student.setFirstName(studentDTO.firstName());
        student.setLastName(studentDTO.lastName());
        student.setEmailAddress(studentDTO.emailAddress());
        student.setAge(studentDTO.age());
        student.setGender(studentDTO.gender());
        student.setPhoneNumber(studentDTO.phoneNumber());

        //Handle creation and modification dates.
        Date currentDate = new Date();
        student.setCreatedDate(currentDate);
        student.setModifiedDate(currentDate);
        return student;
    }

    public ApiResponse addStudent(@Valid StudentDTO studentDTO) {
        try {
            StudentEntity student = populateStudentEntity(studentDTO);
            //Save the new employee record
            studentRepository.save(student);
            return ResponseEntityUtil.populateApiResponse("Successfully Added Employee", student, HttpStatus.CREATED); //201
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntityUtil.populateApiResponse("An error occurred while adding the employee ", null, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    public ApiResponse getAllStudent() {
        try {
            var allStudentDetails = studentRepository.findAll();
            if (allStudentDetails.isEmpty()) {
                log.info("No Records found in the database");
                return ResponseEntityUtil.populateApiResponse("No student record found", Collections.emptyList(), HttpStatus.NOT_FOUND); //404
            }
            log.info("{} student record retrieved successfully", allStudentDetails.size());
            return ResponseEntityUtil.populateApiResponse("Student record retrieved successfully", allStudentDetails, HttpStatus.OK); //200
        } catch (Exception e) {
            log.error("Error while fetching student records", e);
            return ResponseEntityUtil.populateApiResponse("An error occurred while adding the employee ", null, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    public ApiResponse getStudent(Long studentId) {
        final ApiResponse[] apiResponses = new ApiResponse[1];
        Consumer<StudentEntity> studentDetailsById = getStudentEntityConsumer(studentId, apiResponses);
        Runnable studentDetailsNotFound = getStudentDetailsNotFound(studentId, apiResponses);
        studentRepository.findById(studentId)
                .ifPresentOrElse(
                        studentDetailsById,
                        studentDetailsNotFound
                );
        return apiResponses[0];
    }

    public ApiResponse getStudentsByReadability(Long studentId) {

        return studentRepository.findById(studentId)
                .map(this::processStudentDetails)
                .orElseGet(() ->handleStudentNotFound(studentId)); //Execute only when student details are empty.
    }

    private ApiResponse processStudentDetails(StudentEntity studentEntity) {

        log.info("Student details found for the Id = {}", studentEntity.getId());
        return ResponseEntityUtil.populateApiResponse("Student found", studentEntity, HttpStatus.OK);  //200
    }

    private static ApiResponse handleStudentNotFound(Long studentId){
        log.warn("Student details not found for the Id = {}", studentId);
        return new ApiResponse("Student Not found with Id : " ,null, HttpStatus.NOT_FOUND); //404
    }

    public ApiResponse updateStudent(Long studentId) {
        final ApiResponse[] apiResponses = new ApiResponse[1];
        Consumer<StudentEntity> studentDetailsById = getStudentEntityConsumer(studentId, apiResponses);
        Runnable studentDetailNotFound = getDetailsNotFound(studentId, apiResponses);
        studentRepository.findById(studentId)
                .ifPresentOrElse( /// If present student details works or Error
                        studentDetailsById,
                        studentDetailNotFound
                );

        return apiResponses[0];
    }
}
