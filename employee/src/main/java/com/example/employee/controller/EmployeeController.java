package com.example.employee.controller;

import com.example.employee.exception.ErroResponse;
import com.example.employee.exception.TooManyRequestException;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmp(@RequestParam int age) {
        return new ResponseEntity<>(employeeService.fetchEmpAgeLargerThan(age), HttpStatus.OK);
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<?> tooManyRequestHandler() {
        System.out.println("In exception");
        return new ResponseEntity<>(new ErroResponse("417"), HttpStatus.TOO_MANY_REQUESTS);
    }
}
