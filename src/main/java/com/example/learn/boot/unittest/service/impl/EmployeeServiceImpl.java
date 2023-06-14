package com.example.learn.boot.unittest.service.impl;

import com.example.learn.boot.unittest.converter.EmployeeConverter;
import com.example.learn.boot.unittest.domain.PersistentEmployeeEntity;
import com.example.learn.boot.unittest.model.EmployeeDTO;
import com.example.learn.boot.unittest.repository.EmployeeRepository;
import com.example.learn.boot.unittest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public  class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> listAll() {

        return employeeConverter.convertToDtoList(employeeRepository.findAll());
    }

    @Override
    public EmployeeDTO save(EmployeeDTO dto) {
        try {
            EmployeeDTO savedDto = employeeConverter.convertToDto(
                    employeeRepository.saveAndFlush(employeeConverter.convertToEntity(dto)));
            return savedDto;

        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public ResponseEntity<EmployeeDTO> updateEmployee(long id, EmployeeDTO employeeDTO) {
        PersistentEmployeeEntity existingEntity = employeeRepository.findById(id).orElse(null);
        try {
            if (existingEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            PersistentEmployeeEntity updatedEntity = employeeRepository.save(employeeConverter.copyConvertToEntity(employeeDTO, existingEntity));
            return ResponseEntity.status(HttpStatus.OK).body(employeeConverter.convertToDto(updatedEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeConverter.convertToDto(existingEntity));
        }
    }

    @Override
    public ResponseEntity<EmployeeDTO> getEmployeeById(long id, EmployeeDTO employeeDTO) {
        PersistentEmployeeEntity existingEntity = employeeRepository.findById(id).orElse(null);
        try {
            if (existingEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(employeeConverter.convertToDto(existingEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(employeeConverter.convertToDto(existingEntity));
        }
    }




//    public EmployeeDTO deleteEmployee(long id){
//        employeeRepository.deleteById(id);
//        return null;
//    }

    @Override
    public ResponseEntity<EmployeeDTO> deleteEmployee(long id, EmployeeDTO employeeDTO){
        PersistentEmployeeEntity existingEntity = employeeRepository.findById(id).orElse(null);

        if (existingEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(employeeConverter.convertToDto(existingEntity));

    }
}



