package com.example.search.controller;

import com.example.search.exception.ErroResponse;
import com.example.search.exception.TooManyRequestException;
import com.example.search.service.EmployeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Recover;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final EmployeeService employeeService;


    public SearchController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/search/employees/u")
    public ResponseEntity<?> relist() {
        return new ResponseEntity<>("Unlock", HttpStatus.OK);
    }



//    @HystrixCommand (fallbackMethod = "default_page")
    @GetMapping("/search/employees")
    public ResponseEntity<?> getDetails(@RequestParam List<Integer> ages) {
        //TODO

       return new ResponseEntity<>(employeeService.fetchAllEmployeesByAges(ages), HttpStatus.OK);



//        if (flag) {
//            ;
//
//            try {
//                temp = new ResponseEntity<>(employeeService.fetchAllEmployeesByAges(ages), HttpStatus.OK);
//                System.out.println(temp.getStatusCodeValue());
//            } catch (Exception e) {
//                temp = new ResponseEntity<>("failed", HttpStatus.OK);
//                flag = false;
//            }
//
//            return temp;
//        }
//        else {
//            return new ResponseEntity<>("You tried too many times, so you are blocked!", HttpStatus.OK);
//        }
    }


//    public ResponseEntity<?> default_page(@RequestParam List<Integer> ages) {
//        return new ResponseEntity<>("redirect 500 page Internal limit exceed", HttpStatus.PAYLOAD_TOO_LARGE);
//    }




//    @ExceptionHandler(TooManyRequestException.class)
//    public ResponseEntity<?> tooManyRequestHandler() {
//        System.out.println("In exception");
//        return new ResponseEntity<>("What am I doing", HttpStatus.TOO_MANY_REQUESTS);
//    }

}
//
//        if (temp.getStatusCode().is5xxServerError()) {
//            System.out.println("failed 5");
//            return new ResponseEntity<>("Fucked", HttpStatus.OK);
//        }
//        else if (temp.getStatusCode().is4xxClientError()) {
//            System.out.println("failed 4");
//            return new ResponseEntity<>("Fucked", HttpStatus.OK);
//        }
//        else if (temp.getStatusCode().is3xxRedirection()) {
//            System.out.println("failed 3");
//            return new ResponseEntity<>("Fucked", HttpStatus.OK);
//        }
//        else if (temp.getStatusCode().is1xxInformational()) {
//            System.out.println("failed 1");
//            return new ResponseEntity<>("Fucked", HttpStatus.OK);
//        }
//        else if (temp.getStatusCode().equals(429)) {
//            System.out.println("failed 429");
//            return new ResponseEntity<>("Fucked", HttpStatus.OK);
//        }
//        else {
//        System.out.println("succ");
//            return temp;}
//        }



