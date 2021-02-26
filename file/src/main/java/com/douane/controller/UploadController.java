package com.douane.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douane.entite.FileResponse;
import com.douane.entite.MyUploadForm;
import com.douane.metier.IFileSystemStorage;

@RestController
@RequestMapping("/api")
public class UploadController {
	@PostMapping("/upload/{id}/{file}")
	public void uploadFile (@RequestParam(value ="file", required = false) MultipartFile file, @PathVariable("id") Long idDemandes) {
		File uploadRootDir = new File("Upload File/" + idDemandes + "/");
		String nameFile = file.getOriginalFilename();
		System.out.println("Ita ve ito anarana ito? :" + nameFile);
		// Create directory if it not exists.
        if (!uploadRootDir.exists()) {
           uploadRootDir.mkdirs();
        }
        //for (MultipartFile file : files) { 
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
        //}
    }
	@PostMapping("uploadMultiFile/{id}/{valeur}")
	public void uploadFiles (MyUploadForm myForm, @PathVariable("id") Long idDemandes, @PathVariable("valeur")String valeur, HttpServletResponse http) throws IOException {
		File uploadRootDir = new File("Upload File/" + idDemandes + "/");
		String nameFile="";
		//System.out.println("Ita ve ito anarana ito? :" + nameFile);
		// Create directory if it not exists.
        if (!uploadRootDir.exists()) {
           uploadRootDir.mkdirs();
        }
       MultipartFile[] files = myForm.getFileDatas();
        for (MultipartFile file : files) { 
	      String name = file.getOriginalFilename();
	      nameFile = name;
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
       http.sendRedirect("http://192.168.10.161:8083/demandes/uploadMultiFile/"+idDemandes +"/"+ nameFile +"/"+valeur ); 
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
