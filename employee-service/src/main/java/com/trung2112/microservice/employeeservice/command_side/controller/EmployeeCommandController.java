package com.trung2112.microservice.employeeservice.command_side.controller;

import com.trung2112.microservice.employeeservice.command_side.command.CreateEmployeeCommand;
import com.trung2112.microservice.employeeservice.command_side.command.DeleteEmployeeCommand;
import com.trung2112.microservice.employeeservice.command_side.command.UpdateEmployeeCommand;
import com.trung2112.microservice.employeeservice.command_side.dto.EmployeeRequestDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        CreateEmployeeCommand command =
                new CreateEmployeeCommand(
                        UUID.randomUUID().toString(),
                        employeeRequestDto.getFirstName(),
                        employeeRequestDto.getLastName(),
                        employeeRequestDto.getKin(),
                        false
                );

        commandGateway.sendAndWait(command);

        return "emmployee added";
    }

    @PutMapping
    public String updateEmployee(@RequestBody EmployeeRequestDto model) {
        UpdateEmployeeCommand command =
                new UpdateEmployeeCommand(model.getEmployeeId(),model.getFirstName(),model.getLastName(),model.getKin(),model.getIsDisciplined());
        commandGateway.sendAndWait(command);
        return "employee updated";
    }
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        commandGateway.sendAndWait(command);
        return "emmployee deleted";
    }
}
