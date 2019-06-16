package com.bhushan.testapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class SummaryItem{

	@SerializedName("T_NAME")
	private String tNAME;

	@SerializedName("T_GENDER")
	private String tGENDER;

	@SerializedName("F_DURATION")
	private String fDURATION;

	@SerializedName("SEATNO")
	private String sEATNO;

	@SerializedName("F_FROM")
	private String fFROM;

	@SerializedName("FDATE")
	private String fDATE;

	@SerializedName("FNAME")
	private String fNAME;

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

	@SerializedName("TCODE")
	private String tCODE;

	@SerializedName("T_AGE")
	private String tAGE;

	@SerializedName("T_MOBNO")
	private String tMOBNO;

	public void setTNAME(String tNAME){
		this.tNAME = tNAME;
	}

	public String getTNAME(){
		return tNAME;
	}

	public void setTGENDER(String tGENDER){
		this.tGENDER = tGENDER;
	}

	public String getTGENDER(){
		return tGENDER;
	}

	public void setFDURATION(String fDURATION){
		this.fDURATION = fDURATION;
	}

	public String getFDURATION(){
		return fDURATION;
	}

	public void setSEATNO(String sEATNO){
		this.sEATNO = sEATNO;
	}

	public String getSEATNO(){
		return sEATNO;
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

	public void setFNAME(String fNAME){
		this.fNAME = fNAME;
	}

	public String getFNAME(){
		return fNAME;
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

	public void setTCODE(String tCODE){
		this.tCODE = tCODE;
	}

	public String getTCODE(){
		return tCODE;
	}

	public void setTAGE(String tAGE){
		this.tAGE = tAGE;
	}

	public String getTAGE(){
		return tAGE;
	}

	public void setTMOBNO(String tMOBNO){
		this.tMOBNO = tMOBNO;
	}

	public String getTMOBNO(){
		return tMOBNO;
	}

	@Override
 	public String toString(){
		return 
			"SummaryItem{" + 
			"t_NAME = '" + tNAME + '\'' + 
			",t_GENDER = '" + tGENDER + '\'' + 
			",f_DURATION = '" + fDURATION + '\'' + 
			",sEATNO = '" + sEATNO + '\'' + 
			",f_FROM = '" + fFROM + '\'' + 
			",fDATE = '" + fDATE + '\'' + 
			",fNAME = '" + fNAME + '\'' + 
			",aRRIVAL = '" + aRRIVAL + '\'' + 
			",dEPARTURE = '" + dEPARTURE + '\'' + 
			",f_TO = '" + fTO + '\'' + 
			",fMODEL = '" + fMODEL + '\'' + 
			",eCOST = '" + eCOST + '\'' + 
			",tCODE = '" + tCODE + '\'' + 
			",t_AGE = '" + tAGE + '\'' + 
			",t_MOBNO = '" + tMOBNO + '\'' + 
			"}";
		}
}