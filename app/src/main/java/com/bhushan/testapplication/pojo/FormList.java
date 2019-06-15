package com.bhushan.testapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FormList{

	@SerializedName("FormElements")
	private List<FormElementsItem> formElements;

	public void setFormElements(List<FormElementsItem> formElements){
		this.formElements = formElements;
	}

	public List<FormElementsItem> getFormElements(){
		return formElements;
	}

	@Override
 	public String toString(){
		return 
			"FormList{" + 
			"formElements = '" + formElements + '\'' + 
			"}";
		}
}