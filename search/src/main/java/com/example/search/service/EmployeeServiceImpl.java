package com.example.search.service;

import com.example.search.exception.TooManyRequestException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final ExecutorService pool;
    private final RestTemplate ribbonRestTemplate;

    @Autowired
    public EmployeeServiceImpl(ExecutorService pool, RestTemplate ribbonRestTemplate) {
        this.pool = pool;
        this.ribbonRestTemplate = ribbonRestTemplate;
    }

    @HystrixCommand(fallbackMethod = "default_page")
    @Override
//    @Retryable(value= HttpServerErrorException.class, maxAttempts = 3, backoff = @Backoff(1000))
    public Map<Integer, Map[]> fetchAllEmployeesByAges(List<Integer> ages){
        System.out.println("Calling Emp");
        List<CompletableFuture> completableFutureList = new ArrayList<>();

        for (int age : ages) {
            completableFutureList.add(

                    CompletableFuture.supplyAsync(
//                            () -> {
//                                try {
//                                    return ribbonRestTemplate.getForObject("http://employee-service/employees?age=" +
//                                            age, HashMap[].class);
//                                } catch (HttpServerErrorException e) {
//                                    System.out.println("in search error");
//                                    throw new TooManyRequestException(HttpStatus.TOO_MANY_REQUESTS, "Fall into customize exception");
//                                }
//                            }
                            () -> ribbonRestTemplate.getForObject("http://employee-service/employees?age=" +
                                            age, HashMap[].class)
                            , pool
                    )
            );
        }

        Map<Integer, Map[]> temp = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(VOID -> {
                    Map<Integer, Map[]> res = new HashMap<>();
                    for (int i = 0; i < completableFutureList.size(); i++) {
                        res.put(ages.get(i), (Map[]) completableFutureList.get(i).join());
                    }
                    return res;
                }).join();


        return temp;
    }

    public Map<Integer, Map[]> default_page(List<Integer> ages){
        throw new TooManyRequestException(HttpStatus.TOO_MANY_REQUESTS, "Fall into customize exception");
    }
}
