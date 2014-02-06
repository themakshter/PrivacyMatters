public class Record {
	private String registrationNumber, organisationName, companiesHouseNumber,
			address, postcode, country, foiFlag, startDate, endDate,
			exemptFlag, tradingName, ukContactFlag, subjectAccessFlag;
	private int descriptionType;
	private final static int OLD_FORMAT= 1;
	private final static int NEW_FORMAT = 2;
	private final static int NEITHER_FORMAT = 3;
}
