package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class DataController {
	private String registrationNumber, organisationName, companiesHouseNumber,
			postcode, country, foiFlag, exemptFlag, tradingName, ukContact,
			subjectAccess, format;
	private Calendar startDate, endDate;
	private ArrayList<String> address;

	// new format
	private NewFormat newFormat;
	// old format
	private ArrayList<Purpose> oldFormat;

	public DataController() {
		registrationNumber = "";
		organisationName = "";
		companiesHouseNumber = "(not given)";
		postcode = "(not given)";
		country = "(not given)";
		foiFlag = "";
		exemptFlag = "";
		tradingName = "(not given)";
		ukContact = "";
		subjectAccess = "";
		format = "";
		address = new ArrayList<String>();
	}

	public void convertOldFormatToNewFormat() {
		newFormat = new NewFormat();
		HashSet<String> dataSubjects, dataClasses, dataDisclosees;
		newFormat.setNatureOfWork("Nature - NotAvailale");
		newFormat.setTransfers(oldFormat.get(0).getTransfers());
		dataSubjects = new HashSet<String>();
		dataClasses = new HashSet<String>();
		dataDisclosees = new HashSet<String>();
		for (Purpose p : oldFormat) {

			// purposes
			newFormat.addPurpose(p.getPurpose());

			// data subjects
			for (String dataSubject : p.getDataSubjects()) {
				dataSubjects.add(dataSubject);
			}

			// data classes
			for (String dataClass : p.getDataClasses()) {
				dataClasses.add(dataClass);
			}

			// data disclosees
			for (String dataDisclosee : p.getDataDisclosees()) {
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
		this.organisationName = organisationName.toUpperCase();
	}

	public String getCompaniesHouseNumber() {
		return companiesHouseNumber;
	}

	public void setCompaniesHouseNumber(String companiesHouseNumber) {
		this.companiesHouseNumber = companiesHouseNumber;
	}

	public void addAdressLine(String line) {
		String addressLine = "";
		String[] words = line.toLowerCase().split(" ");
		for (String s : words) {
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
			addressLine += s + " ";
		}
		address.add(addressLine.trim());
	}

	public ArrayList<String> getAddress() {
		return address;
	}

	public String getAddressAsLine() {
		return "";
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
		this.country="";
		for(String s : country.split(" ")){
			this.country += s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ";
		}
		this.country = this.country.trim();
	}

	public String getFoiFlag() {
		return foiFlag;
	}

	public void setFoiFlag(String foiFlag) {
		this.foiFlag = foiFlag;
	}

	public String getStartDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEEs, d MMMM, yyyy");
		String date = sdf.format(startDate.getTime());
		return date;
	}

	public void setStartDate(String startDate) {
		this.startDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.startDate.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public String getEndDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM, yyyy");
		String date = sdf.format(endDate.getTime());
		return date;
	}

	public void setEndDate(String endDate) {
		this.endDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.endDate.setTime(sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

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

	public void setUkContact(String ukContact) {
		this.ukContact = ukContact;
	}

	public String getSubjectAccess() {
		return subjectAccess;
	}

	public void setSubjectAccess(String subjectAccess) {
		this.subjectAccess = subjectAccess;
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

	public void addPurpose(Purpose purpose) {
		oldFormat.add(purpose);
	}

}
