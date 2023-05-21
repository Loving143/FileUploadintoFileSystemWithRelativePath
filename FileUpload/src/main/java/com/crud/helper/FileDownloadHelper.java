package com.crud.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

public class FileDownloadHelper {
	
	private Path foundFile;
	public final String uploadDir=new ClassPathResource("static/image/").getFile().getAbsolutePath(); 
	public FileDownloadHelper() throws IOException
	{
		System.out.println(uploadDir+" This is the directory ");
		
		
		Path upload=Paths.get(uploadDir);
		Files.list(upload).forEach(file->
		{
			if(file.getFileName().toString().startsWith(uploadDir))
			{
				foundFile=file;
				return;
			}
		});
		
		if(foundFile!=null)
		{
			return new ClassPathResource(foundFile.toUri());
		}
		
	}
	
	
	
		
	
		


}
