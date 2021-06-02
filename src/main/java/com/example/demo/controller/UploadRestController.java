package com.example.demo.controller;

import java.util.Date;

import javax.servlet.ServletContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/demo")
public class UploadRestController implements ServletContextAware{
	private ServletContext servletContext;
	@PostMapping(value="upload")
	public ResponseEntity<Object> upload(@RequestParam("files") MultipartFile[] files){
		try {
			System.out.println(" files **** "+files);
			for(MultipartFile file: files) {
				System.out.println("file name :"+file.getOriginalFilename());
				System.out.println("file size :"+file.getSize());
				System.out.println("file type :"+file.getContentType());
				save(file);
			}
			//return new ResponseEntity<Void>(HttpStatus.OK);
			return new ResponseEntity<Object>("The File Uploaded Successfully", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	private String save(MultipartFile file) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			String newFileName = sdf.format(new Date())+file.getOriginalFilename();
			System.out.println(" file in save "+ newFileName);
			byte[] bytes = file.getBytes();
			//Path path = Paths.get(this.servletContext.getRealPath("/uploads/"+newFileName));
			Path path = Paths.get("/home/jamunas/jamuna_work/uploadFiles/"+newFileName);
			System.out.println(" Paths : "+path);
			Files.write(path, bytes);
			return newFileName;
		}
		catch(Exception e) {
			return null;
		}
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
}
