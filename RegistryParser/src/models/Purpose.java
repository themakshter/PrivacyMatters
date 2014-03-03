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

	public void setPurpose(String purpose) {
		this.purpose = purpose.replaceAll("&", "and");		
	}

	public String getPurpose() {
		return purpose;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setFurtherDescription(String further) {
		furtherDescription = further;
	}

	public String getFurtherDescription() {
		return furtherDescription;
	}

	public void addDataSubject(String dataSubject){
		dataSubjects.add(dataSubject);
	}
	
	public void setDataSubjects(ArrayList<String> subjects) {
		this.dataSubjects = subjects;
	}

	public ArrayList<String> getDataSubjects() {
		return dataSubjects;
	}

	public void addDataClass(String dataClass){
		dataClasses.add(dataClass);
	}
	
	public void setDataClasses(ArrayList<String> classes) {
		this.dataClasses = classes;
	}

	public ArrayList<String> getDataClasses() {
		return dataClasses;
	}
	
	public void addDataDisclosee(String dataDisclosee){
		dataDisclosees.add(dataDisclosee);
	}

	public void setDataDisclosees(ArrayList<String> disclosees) {
		this.dataDisclosees = disclosees;
	}

	public ArrayList<String> getDataDisclosees() {
		return dataDisclosees;
	}

	public void setTransfers(String transfers) {
		this.transfers = transfers;
	}

	public String getTransfers() {
		return transfers;
	}
}
