import java.util.ArrayList;

public class Record {
	private String registrationNumber, organisationName, companiesHouseNumber,
			address, postcode, country, foiFlag, startDate, endDate,
			exemptFlag, tradingName, ukContactFlag, subjectAccessFlag;
	private int descriptionType;
	private final static int OLD_FORMAT = 1;
	private final static int NEW_FORMAT = 2;

	// new format
	NatureOfWork newFormat;
	// old format
	ArrayList<Purpose> oldFormat;

	public Record() {
		registrationNumber = "";
		organisationName = "";
		companiesHouseNumber = "(not given)";
		address = "";
		postcode = "";
		country = "";
		foiFlag = "";
		startDate = "";
		endDate = "";
		exemptFlag = "";
		tradingName = "";
		ukContactFlag = "";
		subjectAccessFlag = "";
		descriptionType = 0;
	}

	public void setType(int type) {
		descriptionType = type;
		switch (descriptionType) {
		case OLD_FORMAT:
			purposesOld = new ArrayList<Purpose>();
		case NEW_FORMAT:
			newFormat = new NatureOfWork();
		}
	}
	
	public void setRegistration(String reg){
		registrationNumber = reg;
	}
	
	public void setOrganisation(String org){
		organisationName = org;
	}
	
	public void setCompaniesHouse(String compHouse){
		companiesHouseNumber = compHouse;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setPostcode(String postCode){
		postcode = postCode;
	}
	
	public void setCountry(String country){
		this.country = country;
	}
	
	public void setFoiFlag(String foi){
		foiFlag = foi;
	}
	
	public void setStartDate(String date){
		startDate = date;		
	}
	
	public void setEndDate(String date){
		endDate = date;
	}
	
	public void setExempt(String flag){
		exemptFlag = flag;
	}
	
	public void setTradingName(String name){
		tradingName = name;
	}
	
	public void setUKContact(String flag){
		ukContactFlag = flag;
	}
	
	public void setSubjectAccess(String flag){
		subjectAccessFlag = flag;
	}
	
	public void setNewFormat(NatureOfWork newForm){
		newFormat = newForm;
	}
	
	public void setOldFormat(ArrayList<Purpose> oldForm){
		oldFormat = oldForm;
	}

	
}
