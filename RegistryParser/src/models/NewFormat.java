package models;
import java.util.ArrayList;

public class NewFormat {
	private ArrayList<String> purposes, dataSubjects, dataClasses,
			dataDisclosees;

	private String natureOfWork, transfers;

	public NewFormat() {
		purposes = new ArrayList<String>();
		dataSubjects = new ArrayList<String>();
		dataClasses = new ArrayList<String>();
		dataDisclosees = new ArrayList<String>();
	}

	public void setNatureOfWork(String nature) {
		natureOfWork = nature.split("-")[1];
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void setPurposes(ArrayList<String> purposes) {
		this.purposes = purposes;
	}

	public ArrayList<String> getPurposes() {
		return purposes;
	}

	public void setSubjects(ArrayList<String> subjects) {
		this.dataSubjects = subjects;
	}

	public ArrayList<String> getSubjects() {
		return dataSubjects;
	}

	public void setClasses(ArrayList<String> classes) {
		this.dataClasses = classes;
	}

	public ArrayList<String> getClasses() {
		return dataClasses;
	}

	public void setDisclosees(ArrayList<String> disclosees) {
		this.dataDisclosees = disclosees;
	}

	public ArrayList<String> getDisclosees() {
		return dataDisclosees;
	}

	public void setTransfers(String transfers) {
		this.transfers = transfers;
	}

	public String getTransfers() {
		return transfers;
	}
}
