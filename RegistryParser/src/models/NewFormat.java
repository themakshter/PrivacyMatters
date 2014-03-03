package models;
import java.util.ArrayList;

public class NewFormat {
	private ArrayList<String> purposes, dataSubjects, dataClasses,sensitiveData,
			dataDisclosees;

	private String natureOfWork, transfers;

	public NewFormat() {
		purposes = new ArrayList<String>();
		dataSubjects = new ArrayList<String>();
		dataClasses = new ArrayList<String>();
		dataDisclosees = new ArrayList<String>();
		sensitiveData = new ArrayList<String>();
	}

	public void setNatureOfWork(String nature) {
		natureOfWork = nature.split("-")[1];
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void addPurpose(String purpose){
		purposes.add(purpose);
	}
	
	public void setPurposes(ArrayList<String> purposes) {
		this.purposes = purposes;
	}

	public ArrayList<String> getPurposes() {
		return purposes;
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

	public ArrayList<String> getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(ArrayList<String> sensitiveData) {
		this.sensitiveData = sensitiveData;
	}
	
	public void addSensitiveData(String sensitiveData){
		this.sensitiveData.add(sensitiveData);
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
