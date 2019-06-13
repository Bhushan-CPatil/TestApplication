package com.bhushan.testapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse{

	@SerializedName("access")
	private boolean access;

	@SerializedName("errormsg")
	private String errormsg;

	public void setAccess(boolean access){
		this.access = access;
	}

	public boolean isAccess(){
		return access;
	}

	public void setErrormsg(String errormsg){
		this.errormsg = errormsg;
	}

	public String getErrormsg(){
		return errormsg;
	}

	@Override
 	public String toString(){
		return 
			"DefaultResponse{" + 
			"access = '" + access + '\'' + 
			",errormsg = '" + errormsg + '\'' + 
			"}";
		}
}