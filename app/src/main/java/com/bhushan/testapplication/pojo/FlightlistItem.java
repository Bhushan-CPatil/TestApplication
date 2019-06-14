package com.bhushan.testapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class FlightlistItem{

	@SerializedName("FID")
	private String fID;

	@SerializedName("ARRIVAL")
	private String aRRIVAL;

	@SerializedName("DEPARTURE")
	private String dEPARTURE;

	@SerializedName("F_TO")
	private String fTO;

	@SerializedName("FMODEL")
	private String fMODEL;

	@SerializedName("ECOST")
	private String eCOST;

	@SerializedName("F_DURATION")
	private String fDURATION;

	@SerializedName("F_FROM")
	private String fFROM;

	@SerializedName("FDATE")
	private String fDATE;

	@SerializedName("BCOST")
	private String bCOST;

	@SerializedName("TOTSEAT")
	private String tOTSEAT;

	@SerializedName("FNAME")
	private String fNAME;

	public void setFID(String fID){
		this.fID = fID;
	}

	public String getFID(){
		return fID;
	}

	public void setARRIVAL(String aRRIVAL){
		this.aRRIVAL = aRRIVAL;
	}

	public String getARRIVAL(){
		return aRRIVAL;
	}

	public void setDEPARTURE(String dEPARTURE){
		this.dEPARTURE = dEPARTURE;
	}

	public String getDEPARTURE(){
		return dEPARTURE;
	}

	public void setFTO(String fTO){
		this.fTO = fTO;
	}

	public String getFTO(){
		return fTO;
	}

	public void setFMODEL(String fMODEL){
		this.fMODEL = fMODEL;
	}

	public String getFMODEL(){
		return fMODEL;
	}

	public void setECOST(String eCOST){
		this.eCOST = eCOST;
	}

	public String getECOST(){
		return eCOST;
	}

	public void setFDURATION(String fDURATION){
		this.fDURATION = fDURATION;
	}

	public String getFDURATION(){
		return fDURATION;
	}

	public void setFFROM(String fFROM){
		this.fFROM = fFROM;
	}

	public String getFFROM(){
		return fFROM;
	}

	public void setFDATE(String fDATE){
		this.fDATE = fDATE;
	}

	public String getFDATE(){
		return fDATE;
	}

	public void setBCOST(String bCOST){
		this.bCOST = bCOST;
	}

	public String getBCOST(){
		return bCOST;
	}

	public void setTOTSEAT(String tOTSEAT){
		this.tOTSEAT = tOTSEAT;
	}

	public String getTOTSEAT(){
		return tOTSEAT;
	}

	public void setFNAME(String fNAME){
		this.fNAME = fNAME;
	}

	public String getFNAME(){
		return fNAME;
	}

	@Override
 	public String toString(){
		return 
			"FlightlistItem{" + 
			"fID = '" + fID + '\'' + 
			",aRRIVAL = '" + aRRIVAL + '\'' + 
			",dEPARTURE = '" + dEPARTURE + '\'' + 
			",f_TO = '" + fTO + '\'' + 
			",fMODEL = '" + fMODEL + '\'' + 
			",eCOST = '" + eCOST + '\'' + 
			",f_DURATION = '" + fDURATION + '\'' + 
			",f_FROM = '" + fFROM + '\'' + 
			",fDATE = '" + fDATE + '\'' + 
			",bCOST = '" + bCOST + '\'' + 
			",tOTSEAT = '" + tOTSEAT + '\'' + 
			",fNAME = '" + fNAME + '\'' + 
			"}";
		}
}