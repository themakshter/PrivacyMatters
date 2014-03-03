package models;

import java.util.ArrayList;

public class NewFormat {
	private ArrayList<String> purposes, dataSubjects, dataClasses,
			dataDisclosees,sensitiveData;

	private String natureOfWork, transfers;

	
	public NewFormat() {
		purposes = new ArrayList<String>();
		dataSubjects = new ArrayList<String>();
		dataClasses = new ArrayList<String>();
		dataDisclosees = new ArrayList<String>();
		sensitiveData = new ArrayList<String>();
	}

	public ArrayList<String> getPurposes() {
		return purposes;
	}

	public void setPurposes(ArrayList<String> purposes) {
		this.purposes = purposes;
	}

	public ArrayList<String> getDataSubjects() {
		return dataSubjects;
	}

	public void setDataSubjects(ArrayList<String> dataSubjects) {
		this.dataSubjects = dataSubjects;
	}

	public ArrayList<String> getDataClasses() {
		return dataClasses;
	}

	public void setDataClasses(ArrayList<String> dataClasses) {
		this.dataClasses = dataClasses;
	}

	public ArrayList<String> getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(ArrayList<String> sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

	
	public ArrayList<String> getDataDisclosees() {
		return dataDisclosees;
	}

	public void setDataDisclosees(ArrayList<String> dataDisclosees) {
		this.dataDisclosees = dataDisclosees;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public String getTransfers() {
		return transfers;
	}

	public void setTransfers(String transfers) {
		this.transfers = transfers;
	}

}
