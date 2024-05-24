package com.trung2112.microservice.employeeservice.query_side.projection;

import com.trung2112.microservice.employeeservice.command_side.entity.Employee;
import com.trung2112.microservice.employeeservice.command_side.repository.EmployeeRepository;
import com.trung2112.microservice.employeeservice.query_side.dto.EmployeeReponseDto;
import com.trung2112.microservice.employeeservice.query_side.query.GetAllEmployeeQuery;
import com.trung2112.microservice.employeeservice.query_side.query.GetEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeReponseDto handle(GetEmployeeQuery getEmployeesQuery) {
        EmployeeReponseDto model = new EmployeeReponseDto();
        Employee employee = employeeRepository.getReferenceById(getEmployeesQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);

        return model;
    }
    @QueryHandler
    public List<EmployeeReponseDto> handle(GetAllEmployeeQuery getAllEmployeeQuery){
        List<EmployeeReponseDto> listModel = new ArrayList<>();
        List<Employee> listEntity = employeeRepository.findAll();
        listEntity.forEach(s -> {
            EmployeeReponseDto model = new EmployeeReponseDto();
            BeanUtils.copyProperties(s, model);
            listModel.add(model);
        });
        return listModel;
    }
}
