package com.subin.test.driven.controller;

import com.subin.test.driven.model.entity.EmployeeEntity;
import com.subin.test.driven.exception.EmployeeNotFoundException;
import com.subin.test.driven.model.dto.EmployeeDTO;
import com.subin.test.driven.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("employee/{empId}")
    public Optional<EmployeeEntity> getEmployee(@PathVariable Long empId){
        return Optional.ofNullable(employeeRepository.findById(empId)
                .orElseThrow(()-> new EmployeeNotFoundException(empId)));
    }

    @PostMapping("employee")
    public EmployeeEntity saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmpId(employeeDTO.empId());
        employeeEntity.setEmpName(employeeDTO.empName());
        employeeEntity.setEmpDept(employeeDTO.empDept());
        employeeEntity.setEmpSalary(employeeDTO.empSalary());
        employeeEntity.setEmpType(employeeDTO.empType() );
        return employeeRepository.save(employeeEntity);
    }

    @PutMapping("employee/{empId}")
    public EmployeeEntity updateEmployee(@Valid @PathVariable Long empId ,@RequestBody EmployeeDTO employeeDTO){
         return employeeRepository.findById(empId)
                 .map(existing -> {
                     existing.setEmpName(employeeDTO.empName());
                     existing.setEmpDept(employeeDTO.empDept());
                     existing.setEmpSalary(employeeDTO.empSalary());
                     existing.setEmpType(employeeDTO.empType());
                     return employeeRepository.save(existing);
                 })
                 .orElseThrow(()-> new EmployeeNotFoundException(empId));
                 //.orElseThrow(EmployeeNotFoundException ::new);
    }

    //Return void or ResponseEntity<Void> with 204 No Content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("employee/{empId}")
    public void deleteEmployee(@PathVariable Long empId){
         employeeRepository.findById(empId)
                 .ifPresentOrElse(
                         existingEmp -> employeeRepository.deleteById(empId),
                         ()->{
                             throw new EmployeeNotFoundException(empId);}
                 );
    }


/*    @DeleteMapping("employee/{empId2}")
    public ResponseEntity<Void> deleteEmployee2(@PathVariable Long empId2) {
        return employeeRepository.findById(empId2)
                .map(emp -> {
                    employeeRepository.deleteById(empId2);
                    return ResponseEntity.noContent().<Void>build();  //This explicitly tells Java that the ResponseEntity being built is of type Void, not just Object.

                })
                .orElseThrow(() -> new EmployeeNotFoundException(empId2));
    }*/
}
