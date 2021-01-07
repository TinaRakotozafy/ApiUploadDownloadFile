package com.douane.entite;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
	private String fileName;
    private String fileUrl;
    private String message;
    
}
