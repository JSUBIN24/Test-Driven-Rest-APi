package com.subin.test.driven.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EmployeeDTO(@NotNull Long empId, @NotEmpty String empName, String empDept, int empSalary, String empType) {
}
