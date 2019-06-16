package com.bhushan.testapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SummaryResponse{

	@SerializedName("summary")
	private List<SummaryItem> summary;

	@SerializedName("access")
	private String access;

	public void setSummary(List<SummaryItem> summary){
		this.summary = summary;
	}

	public List<SummaryItem> getSummary(){
		return summary;
	}

	public void setAccess(String access){
		this.access = access;
	}

	public String getAccess(){
		return access;
	}

	@Override
 	public String toString(){
		return 
			"SummaryResponse{" + 
			"summary = '" + summary + '\'' + 
			",access = '" + access + '\'' + 
			"}";
		}
}