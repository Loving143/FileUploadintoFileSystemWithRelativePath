package com.crud.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
//Teeer Kaa vggdhdkdkujyjhcggd
@Component
public class FileUploadHelper {
	
	//public final String uploadDir="D:\\Study Material\\Projects\\Blogging Application\\UploadingAndDownloadingByDurgesh\\src\\main\\resources\\static";
//	public final String uploadDir=new ClassPathResource("static/image/").getFile().getAbsolutePath(); 
	
	public final String uploadDir=new ClassPathResource("static/image/").getFile().getAbsolutePath(); 
	
	
	
	//It will only run when the constructor is being called . 
	//Basically it will provide me the path till the resources.
	
	//I will create a default constructor. 
	public FileUploadHelper() throws IOException
	{
		System.out.println(uploadDir+" This is the directory ");
		
	}
	
	
	public boolean uploadFile(MultipartFile file) throws IOException
	{
		boolean cv=false;
	
//			try {
//				InputStream is =file.getInputStream();
//				
//			byte[]data=new byte[is.available()];
//			is.read(data);
//			//Image ka pura ka pura data is data me rakha hua h.
//			//Agar data rakha hua h to ise read karna padega .
//			
//			FileOutputStream fos=new FileOutputStream(uploadDir+"\\"+file.getOriginalFilename());
//			fos.write(data);
//			
//			fos.flush();
//			fos.close();
		
		
		//In the above code we have done the implementation that is the approach number 1 and the shortcut
		//approach we have written below .So in this we can do the uploading of the  file . 
		try
		{
			cv=true;
			
			Files.copy(file.getInputStream(), Paths.get(uploadDir+"\\"+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			
			cv=true;
			 	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return cv;
		}
	

}
