package com.subin.test.driven.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public record StudentDTO(
       @NotEmpty(message = "First Name is Mandatory") String firstName ,
       @NotBlank(message =  "Second Name is Mandatory") String lastName,
       @Pattern(regexp = "Male|Female|Others", message = "Gender must be Male,Female, or Other") String gender,
       @Email(message = "Email should be valid") @NotBlank(message = "Email is mandatory") String emailAddress,
       @Length(min = 10 , max = 10, message = "Mobile number length should be 10 digits") String phoneNumber,
       int age, Date createdDate, Date modifiedDate
) {
}
