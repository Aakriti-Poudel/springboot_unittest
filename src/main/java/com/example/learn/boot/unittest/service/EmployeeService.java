package com.example.learn.boot.unittest.service;

import com.example.learn.boot.unittest.model.EmployeeDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeDTO> listAll();

    EmployeeDTO save(EmployeeDTO dto);


//    EmployeeDTO getEmployeeById(long id);
//    ResponseEntity<EmployeeDTO> getEmployeeById(long id);

    ResponseEntity<EmployeeDTO> updateEmployee(long id, EmployeeDTO employeeDTO);

//    EmployeeDTO deleteEmployee(long id);
ResponseEntity<EmployeeDTO> deleteEmployee(long id, EmployeeDTO employeeDTO);

    ResponseEntity<EmployeeDTO> getEmployeeById(long id, EmployeeDTO employeeDTO);



}
