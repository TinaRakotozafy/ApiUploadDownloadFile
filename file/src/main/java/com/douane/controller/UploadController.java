package com.douane.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douane.entite.FileResponse;
import com.douane.metier.IFileSystemStorage;

@RestController
@RequestMapping("/api")
public class UploadController {
	@PostMapping("/upload/{id}/")
	public void uploadFile (@RequestParam("files") MultipartFile[] files, @PathVariable("id") Long idDemandes) {
		File uploadRootDir = new File("Upload File/" + idDemandes + "/");
		// Create directory if it not exists.
        if (!uploadRootDir.exists()) {
           uploadRootDir.mkdirs();
        }
        for (MultipartFile file : files) { 
	        String name = file.getOriginalFilename();
	        if (name != null && name.length() > 0) {
		        try {
		        	
		        	File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
		        	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		            stream.write(file.getBytes());
		            stream.close();
		        }catch (Exception e) {
		        	System.out.println("Error Write file");
		        }
	        }
        }
    }
	 @GetMapping("/download/{idDemande}/{fileName}")
		public ResponseEntity<Resource> downloadFiles(@PathVariable("fileName") String fileName, @PathVariable("idDemande") Long id) {
			Path path = Paths.get("Upload File/"+ id +"/"+ fileName);
			Resource resource = null;
			try {
				resource = new UrlResource(path.toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}
}
