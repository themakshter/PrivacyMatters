import java.util.ArrayList;

public class Record {
	private String registrationNumber, organisationName, companiesHouseNumber,
			address, postcode, country, foiFlag, startDate, endDate,
			exemptFlag, tradingName, ukContactFlag, subjectAccessFlag;
	private int descriptionType;
	private final static int OLD_FORMAT = 1;
	private final static int NEW_FORMAT = 2;
	private final static int NEITHER_FORMAT = 3;

	// new format
	private NatureOfWork newFormat;
	// old format
	private ArrayList<Purpose> oldFormat;

	public Record() {
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
		ukContactFlag = "";
		subjectAccessFlag = "";
		descriptionType = 0;
	}

	public void setType(int type) {
		descriptionType = type;
		switch (descriptionType) {
		case OLD_FORMAT:
			oldFormat = new ArrayList<Purpose>();
			break;
		case NEW_FORMAT:
			newFormat = new NatureOfWork();
			break;
		case NEITHER_FORMAT:
			break;
		default:
			break;
		}
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

	public String getUkContactFlag() {
		return ukContactFlag;
	}

	public void setUkContactFlag(String ukContactFlag) {
		this.ukContactFlag = ukContactFlag;
	}

	public String getSubjectAccessFlag() {
		return subjectAccessFlag;
	}

	public void setSubjectAccessFlag(String subjectAccessFlag) {
		this.subjectAccessFlag = subjectAccessFlag;
	}

	public int getDescriptionType() {
		return descriptionType;
	}

	public void setDescriptionType(int descriptionType) {
		this.descriptionType = descriptionType;
	}

	public NatureOfWork getNewFormat() {
		return newFormat;
	}

	public void setNewFormat(NatureOfWork newFormat) {
		this.newFormat = newFormat;
	}

	public ArrayList<Purpose> getOldFormat() {
		return oldFormat;
	}

	public void setOldFormat(ArrayList<Purpose> oldFormat) {
		this.oldFormat = oldFormat;
	}

}
