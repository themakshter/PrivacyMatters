package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
		startDate = new GregorianCalendar(0,0,0);
		endDate =  new GregorianCalendar(0,0,0);
		foiFlag = "";
		exemptFlag = "";
		tradingName = "(not given)";
		ukContact = "";
		subjectAccess = "";
		format = "";
		oldFormat = new ArrayList<Purpose>();
	}

	public void fixName() {
		organisationName = organisationName.replaceAll("andAMP", "&");
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
		this.country = country;
	}

	public String getFoiFlag() {
		return foiFlag;
	}

	public void setFoiFlag(String foiFlag) {
		this.foiFlag = foiFlag;
	}

	public String getStartDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
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
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
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
		this.format = format;
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

}
