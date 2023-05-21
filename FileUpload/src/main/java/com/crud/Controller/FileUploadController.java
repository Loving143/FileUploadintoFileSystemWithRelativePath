//package com.crud.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import com.smart.Helper.FileUploadHelper;
//
//@RestController
//public class FileUploadController 
//{
//	@Autowired
//	private FileUploadHelper fileUploadHelper ;
//
//	@PostMapping("uploadd-file")
//	public ResponseEntity<String> uplaodFile(@RequestParam("file")MultipartFile file)
//	{
//		System.out.println(file.getOriginalFilename());
//		System.out.println(file.getName());
//		System.out.println(file.getSize());
//		
//		if(file.isEmpty())
//		{ 
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request Must contain fle");
//		}        
////		else if(!file.getContentType().equals("image/jpeg"))
////		{
////			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only JPEG Content type are allowed");
////		}
//		
//		try
//		{
//			boolean cv=fileUploadHelper.uploadFile(file);
//			
//			if(cv)
//			{ 
//				//This will return the actual path of the file .In earlier we used to return wheteher the message is suceesful or not .
//				//But this time we are getting the path of the file .
//				String gh=ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
//			return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());	
//			} 
//			
//		}
//		catch(Exception ex)
//		{
//			
//		}
//		
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went Wrong ! Try again "); 
//	}
//	
//}
