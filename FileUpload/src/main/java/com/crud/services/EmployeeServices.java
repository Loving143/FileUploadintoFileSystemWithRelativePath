package com.crud.services;

import java.util.List;

import com.crud.Entities.Employee;

public interface EmployeeServices {

	public List<Employee>getCourses();
	public Employee getCourse(Integer courseId);
	public Employee addCourse(Employee course);
	public Employee updateCourse(Integer id, String imagePath, String signaturePath);
	public void deleteCourse(Integer parseLong);
	
}
