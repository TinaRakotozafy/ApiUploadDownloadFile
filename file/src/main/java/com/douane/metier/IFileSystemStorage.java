package com.douane.metier;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileSystemStorage {
	public void init();
	public String saveFile(MultipartFile file);
	public Resource loadFile(String name);
}
