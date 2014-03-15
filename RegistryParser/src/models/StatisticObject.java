package models;

import java.util.HashSet;

public class StatisticObject {
	private String type;
	private HashSet<RegistryListItem> companies;
	private int size,medianDataClasses,medianSensitiveData,medianDataSubjects,medianDataDisclosees;

	public StatisticObject(){
		companies = new HashSet<RegistryListItem>();	
	}

	public int getMedianDataClasses() {
		return medianDataClasses;
	}

	public void setMedianDataClasses(int medianDataClasses) {
		this.medianDataClasses = medianDataClasses;
	}

	public int getMedianSensitiveData() {
		return medianSensitiveData;
	}

	public void setMedianSensitiveData(int medianSensitiveData) {
		this.medianSensitiveData = medianSensitiveData;
	}

	public int getMedianDataSubjects() {
		return medianDataSubjects;
	}

	public void setMedianDataSubjects(int medianDataSubjects) {
		this.medianDataSubjects = medianDataSubjects;
	}

	public int getMedianDataDisclosees() {
		return medianDataDisclosees;
	}

	public void setMedianDataDisclosees(int medianDataDisclosees) {
		this.medianDataDisclosees = medianDataDisclosees;
	}
	
	
	
	public void addCompany(RegistryListItem company){
		companies.add(company);
		size = companies.size();
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashSet<RegistryListItem> getCompanies() {
		return companies;
	}

	public void setCompanies(HashSet<RegistryListItem> companies) {
		this.companies = companies;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
