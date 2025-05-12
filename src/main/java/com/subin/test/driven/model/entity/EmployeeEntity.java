package com.subin.test.driven.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class EmployeeEntity {

    @Id
    @JsonIgnore
    private Long empId;

    @NotEmpty
    private String empName;
    private String empDept;
    private int empSalary;
    private String empType;

    @Version
    private Integer version;


    public EmployeeEntity() {
    }

    public EmployeeEntity(Long empId, String empName, String empDept, int empSalary, String empType) {
        this.empId = empId;
        this.empName = empName;
        this.empDept = empDept;
        this.empSalary = empSalary;
        this.empType = empType;
    }
}
