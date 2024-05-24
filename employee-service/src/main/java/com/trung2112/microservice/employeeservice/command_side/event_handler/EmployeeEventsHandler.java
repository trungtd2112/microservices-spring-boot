package com.trung2112.microservice.employeeservice.command_side.event_handler;

import com.trung2112.microservice.employeeservice.command_side.entity.Employee;
import com.trung2112.microservice.employeeservice.command_side.event.EmployeeCreatedEvent;
import com.trung2112.microservice.employeeservice.command_side.event.EmployeeDeletedEvent;
import com.trung2112.microservice.employeeservice.command_side.event.EmployeeUpdatedEvent;
import com.trung2112.microservice.employeeservice.command_side.repository.EmployeeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventsHandler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        Employee  employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        Employee employee = employeeRepository.getById(event.getEmployeeId());
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDisciplined(event.getIsDisciplined());
        employeeRepository.save(employee);
    }
    @EventHandler
    public void on(EmployeeDeletedEvent event) {
        employeeRepository.deleteById(event.getEmployeeId());
    }
}
