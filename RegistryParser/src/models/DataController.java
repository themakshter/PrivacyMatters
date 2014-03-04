package models;

import java.util.ArrayList;
import java.util.HashSet;

public class DataController {
	private String registrationNumber, organisationName, companiesHouseNumber,
			address, postcode, country, foiFlag, startDate, endDate,
			exemptFlag, tradingName, ukContact, subjectAccess, format;

	// new format
	private NewFormat newFormat;
	// old format
	private ArrayList<Purpose> oldFormat;

	public DataController() {
		registrationNumber = "";
		organisationName = "";
		companiesHouseNumber = "(not given)";
		address = "";
		postcode = "(not given)";
		country = "(not given)";
		foiFlag = "";
		startDate = "";
		endDate = "";
		exemptFlag = "";
		tradingName = "(not given)";
		ukContact = "";
		subjectAccess = "";
		format = "";
	}
	
	public void convertOldFormatToNewFormat(){
		newFormat = new NewFormat();
		HashSet<String> dataSubjects,dataClasses,dataDisclosees;
		newFormat.setNatureOfWork("Nature - NotAvailale");
		newFormat.setTransfers(oldFormat.get(0).getTransfers());
		dataSubjects = new HashSet<String>();
		dataClasses = new HashSet<String>();
		dataDisclosees = new HashSet<String>();		
		for(Purpose p : oldFormat){
			
			//purposes
			newFormat.addPurpose(p.getPurpose());
			
			//data subjects
			for(String dataSubject: p.getDataSubjects()){
				dataSubjects.add(dataSubject);
			}
			
			//data classes
			for(String dataClass: p.getDataClasses()){
				dataClasses.add(dataClass);
			}
			
			//data disclosees
			for(String dataDisclosee : p.getDataDisclosees()){
				dataDisclosees.add(dataDisclosee);
			}
		}
		newFormat.setDataSubjects(new ArrayList<String>(dataSubjects));
		newFormat.setDataClasses(new ArrayList<String>(dataClasses));
		newFormat.setDataDisclosees(new ArrayList<String>(dataDisclosees));		
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;	
	}

	public String getCompaniesHouseNumber() {
		return companiesHouseNumber;
	}

	public void setCompaniesHouseNumber(String companiesHouseNumber) {
		this.companiesHouseNumber = companiesHouseNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFoiFlag() {
		return foiFlag;
	}

	public void setFoiFlag(String foiFlag) {
		this.foiFlag = foiFlag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getExemptFlag() {
		return exemptFlag;
	}

	public void setExemptFlag(String exemptFlag) {
		this.exemptFlag = exemptFlag;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getUkContact() {
		return ukContact;
	}

	public void setUkContact(String ukContactFlag) {
		this.ukContact = ukContactFlag;
	}

	public String getSubjectAccess() {
		return subjectAccess;
	}

	public void setSubjectAccess(String subjectAccessFlag) {
		this.subjectAccess = subjectAccessFlag;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format.toLowerCase();
		switch (format) {
		case "old":
			oldFormat = new ArrayList<Purpose>();
			break;
		case "new":
			newFormat = new NewFormat();
			break;
		case "neither":
			break;
		default:
			break;
		}
	}

	public NewFormat getNewFormat() {
		return newFormat;
	}

	public void setNewFormat(NewFormat newFormat) {
		this.newFormat = newFormat;
	}

	public ArrayList<Purpose> getOldFormat() {
		return oldFormat;
	}

	public void setOldFormat(ArrayList<Purpose> oldFormat) {
		this.oldFormat = oldFormat;
	}
	
	public void addPurpose(Purpose purpose){
		oldFormat.add(purpose);
	}
	
	

}
