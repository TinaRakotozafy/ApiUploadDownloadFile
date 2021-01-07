package com.douane.entite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileNotFoundException extends RuntimeException{
	private String message;
}
