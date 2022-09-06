package com.example.employee.domain;

import lombok.Data;

/**
 * id": 1,
 * "employee_name": "Tiger Nixon",
 * "employee_salary": 320800,
 * "employee_age": 61,
 * "profile_image": ""
 */
@Data
public class EmployeeDTO {
    private String id;
    private String name;
    private int age;
    private boolean active;
}
