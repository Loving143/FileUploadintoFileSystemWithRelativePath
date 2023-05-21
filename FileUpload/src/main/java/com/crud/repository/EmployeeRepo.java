package com.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.Entities.Employee;

public interface EmployeeRepo  extends JpaRepository<Employee ,Integer>{
	
	
}
