package com.example.tool;

public class StringHelper {
	public static  boolean  isNotEmpty(String value){
		return (null != value && !"null".equals(value) && !value.isEmpty());
	}
	
	public static  boolean  isEmpty(String value){
		return !isNotEmpty(value);
	}
}
