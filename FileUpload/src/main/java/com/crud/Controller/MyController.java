package com.crud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crud.Entities.Employee;
import com.crud.helper.FileUploadHelper;
import com.crud.services.EmployeeServices;
@RestController
@CrossOrigin(origins="*", allowedHeaders="*")
public class MyController {
  
	private String imagePath ="";
	private String signaturePath="";
	
	@Autowired
	private FileUploadHelper fileUploadHelper ;

	@PostMapping("upload-image")
	public ResponseEntity<String> uplaodFile(@RequestParam("file")MultipartFile file)
	{
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getName());
		System.out.println(file.getSize());   
		
		if(file.isEmpty())
		{ 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request Must contain fle");
		}        
//		else if(!file.getContentType().equals("image/jpeg"))
//		{
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only JPEG Content type are allowed");
//		}
		
		try
		{
			boolean cv=fileUploadHelper.uploadFile(file);
			
			if(cv)
			{ 
				//This will return the actual path of the file .In earlier we used to return wheteher the message is suceesful or not .
				//But this time we are getting the path of the file .
			imagePath=ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
			return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());	
			} 
			
		}
		catch(Exception ex)
		{
			
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went Wrong ! Try again "); 
	}
	
	

	@PostMapping("upload-signature")
	public ResponseEntity<String> uplaodSignature(@RequestParam("file")MultipartFile file)
	{
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getName());
		System.out.println(file.getSize());
		
		if(file.isEmpty())
		{ 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request Must contain fle");
		}        
//		else if(!file.getContentType().equals("image/jpeg"))
//		{
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only JPEG Content type are allowed");
//		}
		
		try
		{
			boolean cv=fileUploadHelper.uploadFile(file);
			
			if(cv)
			{ 
				//This will return the actual path of the file .In earlier we used to return wheteher the message is suceesful or not .
				//But this time we are getting the path of the file .
			signaturePath=ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
			return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());	
			} 
			
		}
		catch(Exception ex)
		{
			
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went Wrong ! Try again "); 
	}
	
	
	
	
	
	@Autowired
	private EmployeeServices services;
	
	@GetMapping("/home") 
	public String home() 
	{
		return "Welcome to course Application ";
	}
	
	//Get the Courses
	@GetMapping("/courses")
	public List<Employee> getCourses()
	{
		return this.services.getCourses();
	}
	
//	//Single Courses get 
//	@GetMapping("/courses/{courseId}")
//	public Course getCourse(@PathVariable ("courseId") String courseId)
//	{
//		return this.services.getCourse(Long.parseLong(courseId));
//	}
	
	//Course Add 
	@PostMapping("/courses")
	public Employee addCourse(@RequestBody Employee course)
	{
		return this.services.addCourse(course);
	}
	
	//Update Course using put Request.
	
	@PutMapping("/coursess/{id}")
	public Employee updateCourse(@PathVariable("id")Integer id)
	{
		
		return this.services.updateCourse(id,imagePath,signaturePath);
	}
//	
//	//Delete the course 
//    @DeleteMapping("/courses/{courseId}")
//    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable String courseId){
//    	
//    	try {
//    		this.services.deleteCourse(Long.parseLong(courseId));
//    		return new ResponseEntity<>(HttpStatus.OK);
//    }
//    	catch(Exception e)
//    	{
//    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  
//    	}
//    }
	
}
