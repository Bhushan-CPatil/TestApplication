package com.bhushan.testapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class HistoryItem{

	@SerializedName("tcost")
	private String tcost;

	@SerializedName("uid")
	private String uid;

	@SerializedName("passenger")
	private String passenger;

	@SerializedName("Fdate")
	private String fdate;

	@SerializedName("TCODE")
	private String tCODE;

	public void setTcost(String tcost){
		this.tcost = tcost;
	}

	public String getTcost(){
		return tcost;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setPassenger(String passenger){
		this.passenger = passenger;
	}

	public String getPassenger(){
		return passenger;
	}

	public void setFdate(String fdate){
		this.fdate = fdate;
	}

	public String getFdate(){
		return fdate;
	}

	public void setTCODE(String tCODE){
		this.tCODE = tCODE;
	}

	public String getTCODE(){
		return tCODE;
	}

	@Override
 	public String toString(){
		return 
			"HistoryItem{" + 
			"tcost = '" + tcost + '\'' + 
			",uid = '" + uid + '\'' + 
			",passenger = '" + passenger + '\'' + 
			",fdate = '" + fdate + '\'' + 
			",tCODE = '" + tCODE + '\'' + 
			"}";
		}
}