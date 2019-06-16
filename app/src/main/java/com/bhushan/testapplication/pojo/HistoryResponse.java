package com.bhushan.testapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse{

	@SerializedName("access")
	private String access;

	@SerializedName("history")
	private List<HistoryItem> history;

	public void setAccess(String access){
		this.access = access;
	}

	public String getAccess(){
		return access;
	}

	public void setHistory(List<HistoryItem> history){
		this.history = history;
	}

	public List<HistoryItem> getHistory(){
		return history;
	}

	@Override
 	public String toString(){
		return 
			"HistoryResponse{" + 
			"access = '" + access + '\'' + 
			",history = '" + history + '\'' + 
			"}";
		}
}