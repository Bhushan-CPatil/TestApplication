package com.bhushan.testapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class FormElementsItem{

	@SerializedName("mobno")
	private String mobno;

	@SerializedName("gender")
	private String gender;

	@SerializedName("name")
	private String name;

	@SerializedName("age")
	private String age;

	public void setMobno(String mobno){
		this.mobno = mobno;
	}

	public String getMobno(){
		return mobno;
	}

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

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"FormElementsItem{" + 
			"mobno = '" + mobno + '\'' + 
			",gender = '" + gender + '\'' + 
			",name = '" + name + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}