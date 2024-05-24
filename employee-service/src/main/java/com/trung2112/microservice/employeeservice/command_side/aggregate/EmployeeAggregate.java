package com.trung2112.microservice.employeeservice.command_side.aggregate;

import com.trung2112.microservice.employeeservice.command_side.command.CreateEmployeeCommand;
import com.trung2112.microservice.employeeservice.command_side.command.DeleteEmployeeCommand;
import com.trung2112.microservice.employeeservice.command_side.command.UpdateEmployeeCommand;
import com.trung2112.microservice.employeeservice.command_side.event.EmployeeCreatedEvent;
import com.trung2112.microservice.employeeservice.command_side.event.EmployeeDeletedEvent;
import com.trung2112.microservice.employeeservice.command_side.event.EmployeeUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class EmployeeAggregate {
    @AggregateIdentifier
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

    public EmployeeAggregate() {}

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command) {
        EmployeeCreatedEvent event = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateEmployeeCommand command) {
        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle(DeleteEmployeeCommand command) {
        EmployeeDeletedEvent event = new EmployeeDeletedEvent();
        event.setEmployeeId(command.getEmployeeId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event) {
        this.employeeId = event.getEmployeeId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getIsDisciplined();
    }
}
