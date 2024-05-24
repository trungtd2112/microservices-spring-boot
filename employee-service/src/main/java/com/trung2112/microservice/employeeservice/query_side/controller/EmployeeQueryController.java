package com.trung2112.microservice.employeeservice.query_side.controller;

import com.trung2112.microservice.employeeservice.query_side.dto.EmployeeReponseDto;
import com.trung2112.microservice.employeeservice.query_side.query.GetAllEmployeeQuery;
import com.trung2112.microservice.employeeservice.query_side.query.GetEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

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
