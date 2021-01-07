package com.douane.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.douane.controller.UploadController;
import com.douane.entite.FileUploadProperties;
@ComponentScan(basePackageClasses = UploadController.class)
@SpringBootApplication
@EnableConfigurationProperties({
	FileUploadProperties.class
})

public class FileApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileApplication.class, args);
	}

}
