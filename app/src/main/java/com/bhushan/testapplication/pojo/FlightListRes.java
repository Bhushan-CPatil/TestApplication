package com.bhushan.testapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FlightListRes {

	@SerializedName("flightlist")
	private List<FlightlistItem> flightlist;

	public void setFlightlist(List<FlightlistItem> flightlist){
		this.flightlist = flightlist;
	}

	public List<FlightlistItem> getFlightlist(){
		return flightlist;
	}

	@Override
 	public String toString(){
		return 
			"FlightListRes{" +
			"flightlist = '" + flightlist + '\'' + 
			"}";
		}
}