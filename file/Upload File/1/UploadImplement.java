package com.douane.metier.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douane.entite.MultipartUtility;
import com.douane.entite.MyUploadForm;
@Service
public class UploadImplement implements IUpload{

	@Override
	public String uploadFile(MyUploadForm myUploadForm, Long id) {
		// TODO Auto-generated method stub
		String charset = "UTF-8";
        //File uploadFile1 = new File("C:/pom.xml");
		MultipartFile[] files = myUploadForm.getFileDatas();
		for(MultipartFile file : files) {

        String requestURL = "http://192.168.10.198:8081/api/upload/"+ id + "/"+file;
 
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addFilePart("fileUpload", file);
 
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
		}
		return null;
	}
}
