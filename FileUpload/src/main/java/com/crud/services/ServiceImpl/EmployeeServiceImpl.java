package com.crud.services.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.crud.Entities.Employee;
import com.crud.repository.EmployeeRepo;
import com.crud.services.EmployeeServices;

@Service
public class EmployeeServiceImpl implements EmployeeServices
{
	@Autowired
private EmployeeRepo courseDao;
	List<Employee>list;
	
	
	public EmployeeServiceImpl() {
//		list=new ArrayList<>();
//		list.add(new Course(143,"Java Course ", "This course has been created by Prateek "));
//		list.add(new Course(144,"Java Course ", "This course has been created by Prateek "));
//		list.add(new Course(145,"Java Course ", "This course has been created by Prateek "));
		
	}
@Override
public List<Employee> getCourses() {

return courseDao.findAll();
}
@Override
public Employee getCourse(Integer courseId) {
		
	return courseDao.findById(courseId).get();
}
@Override
public Employee addCourse(Employee course) {
	// TODO Auto-generated method stub
	courseDao.save(course);
	
	return course;
}

public Employee updateCourse(Employee course) {
	courseDao.save(course);
	return course;
}

public void deleteCourse(Integer parseLong)
{
	Employee entity=courseDao.findById(parseLong).get();
	courseDao.delete(entity);
	
}
@Override
public Employee updateCourse(Integer id, String imagePath, String signaturePath) {
	// TODO Auto-generated method stub
Optional<Employee> course=courseDao.findById(id);

Employee c=course.get();
c.setImage(imagePath);
c.setSignature(signaturePath);
courseDao.save(c);

return c;


}


}
