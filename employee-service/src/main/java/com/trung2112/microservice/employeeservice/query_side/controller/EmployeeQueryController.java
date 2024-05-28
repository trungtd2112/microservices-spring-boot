package com.trung2112.microservice.employeeservice.query_side.controller;

import com.trung2112.microservice.employeeservice.query_side.dto.EmployeeReponseDto;
import com.trung2112.microservice.employeeservice.query_side.query.GetAllEmployeeQuery;
import com.trung2112.microservice.employeeservice.query_side.query.GetEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/send/{message}")
    public void producerBinding(@PathVariable String message){
        System.out.println("message: " + message);
        streamBridge.send("trung2112", message);
    }

    @GetMapping("/{employeeId}")
    public EmployeeReponseDto getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeeQuery getEmployeeQuery = new GetEmployeeQuery();
        getEmployeeQuery.setEmployeeId(employeeId);

        EmployeeReponseDto employeeReponseDto =
                queryGateway.query(getEmployeeQuery,
                                ResponseTypes.instanceOf(EmployeeReponseDto.class))
                        .join();

        return employeeReponseDto;
    }

    @GetMapping
    public List<EmployeeReponseDto> getAllEmployee(){
        List<EmployeeReponseDto> list = queryGateway.query(new GetAllEmployeeQuery(), ResponseTypes.multipleInstancesOf(EmployeeReponseDto.class))
                .join();
        return list;
    }


}
