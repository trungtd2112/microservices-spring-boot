package com.trung2112.microservice.employeeservice.command_side.repository;

import com.trung2112.microservice.employeeservice.command_side.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
