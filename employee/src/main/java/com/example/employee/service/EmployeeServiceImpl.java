package com.example.employee.service;

import com.example.employee.domain.EmployeeDTO;
import com.example.employee.domain.EmployeeResponseDTO;
import com.example.employee.exception.TooManyRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{


    private final RestTemplate restTemplate;

    @Value("${employee-endpoint}")
    private String url;

    @Autowired
    public EmployeeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Retryable(value= TooManyRequestException.class, maxAttempts = 3)
    @Override
    public List<EmployeeDTO> fetchEmpAgeLargerThan(int age) throws TooManyRequestException{
        System.out.println("In server");

        try {
            EmployeeResponseDTO employeeResponseDTO = restTemplate.getForObject(url, EmployeeResponseDTO.class);

            return employeeResponseDTO.getEmployees()
                    .stream()
                    .filter(e -> e.getAge() >= age)
                    .collect(Collectors.toList());
        }
        catch (TooManyRequestException e) {
            System.out.println("Emp catch");
            throw new TooManyRequestException(HttpStatus.BAD_REQUEST, "Fall into customize exception");
        }
    }

//    @Recover
//    public ResponseEntity<String> recover(TooManyRequestException e) {
//        System.out.println("FFFFFFFFFFFFFFFFF");
//        return restTemplate.getForEntity("FFFFFFF", String.class);
//    }
}
