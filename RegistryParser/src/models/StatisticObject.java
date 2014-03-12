package models;

import java.util.HashSet;

public class StatisticObject {
	private String type;
	private HashSet<RegistryListItem> companies;
	private int size;
	
	public StatisticObject(){
		companies = new HashSet<RegistryListItem>();	
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
