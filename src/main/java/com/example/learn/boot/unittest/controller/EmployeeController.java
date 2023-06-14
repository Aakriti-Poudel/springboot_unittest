package com.example.learn.boot.unittest.controller;

import com.example.learn.boot.unittest.model.EmployeeDTO;
import com.example.learn.boot.unittest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping({"/", "/list"})
    @ResponseBody
    public ResponseEntity<List<EmployeeDTO>> employeeList() {
        List<EmployeeDTO> employeeDTO = employeeService.listAll();
        if (employeeDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);

    }

//    @PostMapping("/save")
//    @ResponseBody
//    public ResponseEntity create(@RequestBody EmployeeDTO dto) {
//        EmployeeDTO savedDTO = employeeService.save(dto);
//        dto =null;
//        try{
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity create(@RequestBody EmployeeDTO dto) {
        EmployeeDTO savedDTO = employeeService.save(dto);
        if(dto.getName() ==null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

//    @GetMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity get(@PathVariable Long id) {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id,  EmployeeDTO employeeDTO) {
        ResponseEntity<EmployeeDTO> dto = employeeService.getEmployeeById(  id, employeeDTO);
        if (dto.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        return ResponseEntity.status(dto.getStatusCode()).body(dto.getBody());
    }


//    @PutMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
//            ResponseEntity<EmployeeDTO> dto = employeeService.updateEmployee(id, employeeDTO);
//            if(dto.getStatusCode().equals(HttpStatus.NOT_FOUND)){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//            if(dto.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
//                return ResponseEntity.status(dto.getStatusCode()).body(dto.getBody());
//            }
//            return ResponseEntity.status(dto.getStatusCode()).body(dto.getBody());
//    }
//    @GetMapping({"/", "/list"})
//    @ResponseBody
//    public ResponseEntity<List<EmployeeDTO>> employeeList(@PathVariable Long id, EmployeeDTO employeeDTO) {
//        List<EmployeeDTO> employeeDTO = employeeService.listAll();
//        if (employeeDTO.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
//
//    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity delete(@PathVariable Long id) {
//        EmployeeDTO employeeDTO = employeeService.deleteEmployee(id);
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Long id, EmployeeDTO employeeDTO) {
        ResponseEntity<EmployeeDTO> dto = employeeService.deleteEmployee(id, employeeDTO);
        if(dto.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(dto.getStatusCode()).body(dto.getBody());
    }
}
