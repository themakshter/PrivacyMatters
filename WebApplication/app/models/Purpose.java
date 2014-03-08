package models;

import java.util.ArrayList;

public class Purpose {
	private String purpose, description, furtherDescription, transfers;

	private ArrayList<String> dataSubjects, dataDisclosees, dataClasses;

	public Purpose() {
		dataSubjects = new ArrayList<String>();
		dataClasses = new ArrayList<String>();
		dataDisclosees = new ArrayList<String>();
		description = "(not given)";
		furtherDescription = "(not given)";
	}

	public String getPurpose() {
		return purpose;
	}
	
	public String getUniquePurpose(){
		return purpose.split(",")[0];
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFurtherDescription() {
		return furtherDescription;
	}

	public void setFurtherDescription(String furtherDescription) {
		this.furtherDescription = furtherDescription;
	}

	public String getTransfers() {
		return transfers;
	}

	public void setTransfers(String transfers) {
		this.transfers = transfers;
	}

	public ArrayList<String> getDataSubjects() {
		return dataSubjects;
	}

	public void setDataSubjects(ArrayList<String> dataSubjects) {
		this.dataSubjects = dataSubjects;
	}

	public ArrayList<String> getDataDisclosees() {
		return dataDisclosees;
	}

	public void setDataDisclosees(ArrayList<String> dataDisclosees) {
		this.dataDisclosees = dataDisclosees;
	}

	public ArrayList<String> getDataClasses() {
		return dataClasses;
	}

	public void setDataClasses(ArrayList<String> dataClasses) {
		this.dataClasses = dataClasses;
	}
}
