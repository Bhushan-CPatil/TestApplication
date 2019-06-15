package com.bhushan.testapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class FormList{

	@SerializedName("gender")
	private String gender;

	@SerializedName("name")
	private String name;

	@SerializedName("mobileno")
	private String mobileno;

	@SerializedName("age")
	private String age;

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobileno(String mobileno){
		this.mobileno = mobileno;
	}

	public String getMobileno(){
		return mobileno;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"FormList{" + 
			"gender = '" + gender + '\'' + 
			",name = '" + name + '\'' + 
			",mobileno = '" + mobileno + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}