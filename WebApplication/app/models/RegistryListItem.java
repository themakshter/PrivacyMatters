package models;

public class RegistryListItem {
	private String registrationNumber,organisationName;

	public RegistryListItem(String regNo,String orgName){
		registrationNumber = regNo;
		organisationName = orgName.replaceAll("andAMP", "&");
		
	}
	
	public String getRegistrationNumber(){
		return registrationNumber;
	}
	
	public String getOrganisationName(){
		return organisationName;
	}
}
