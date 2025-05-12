package com.subin.test.driven.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    @NotBlank(message = "Lastname is mandatory")
    private String lastName;
    private String gender;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String emailAddress;
    @Length(min = 10, max = 10, message = "Mobile number length should be 10 digits")
    private String phoneNumber;
    private int age;
    private Date createdDate;
    private Date modifiedDate;

}
