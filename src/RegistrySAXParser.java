import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class RegistrySAXParser {
	public static void main(String argv[]) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				private int recordCount,regNumCount,orgNameCount,companiesHouseCount,postcodeCount,countryCount,foiCount,startDateCount,endDateCount,exemptFlagCount,tradingNameCount,ukContactCount,subjectAccessCount,natureOfWorkCount,newBlobCount,oldBlobCount = 0;
				private int type = 0;
				private static final int REGISTRATION_NUMBER = 1;
				private static final int ORGANISATION_NAME = 2;
				private static final int COMPANIES_HOUSE_NUMBER = 3;
				private static final int ADDRESS_1 = 4;
				private static final int ADDRESS_2 = 5;
				private static final int ADDRESS_3 = 6;
				private static final int ADDRESS_4 = 7;
				private static final int ADDRESS_5 = 8;
				private static final int POSTCODE = 9;
				private static final int COUNTRY = 10;
				private static final int FOI = 11;
				private static final int START_DATE = 12;
				private static final int END_DATE = 13;
				private static final int EXEMPT_FLAG = 14;
				private static final int TRADING_NAME = 15;
				private static final int UK_CONTACT = 17;
				private static final int SUBJECT_ACCESS_CONTACT = 18;
				private static final int NATURE_OF_WORK = 19;
				private static final int REGISTRATION = 20;
				private static final int RECORD = 21;
				private String address = "\tAddress : ";

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					switch (qName.toUpperCase()) {
					case "REGISTRATION":
						type = REGISTRATION;
						break;
					case "RECORD":
						type = RECORD;
						recordCount++;
						break;
					case "REGISTRATION_NUMBER":
						type = REGISTRATION_NUMBER;
						regNumCount++;
						break;
					case "ORGANISATION_NAME":
						type = ORGANISATION_NAME;
						orgNameCount++;
						break;
					case "COMPANIES_HOUSE_NUMBER":
						type = COMPANIES_HOUSE_NUMBER;
						companiesHouseCount++;
						break;
					case "ORGANISATION_ADDRESS_LINE_1":
						type = ADDRESS_1;
						break;
					case "ORGANISATION_ADDRESS_LINE_2":
						type = ADDRESS_2;
						break;
					case "ORGANISATION_ADDRESS_LINE_3":
						type = ADDRESS_3;
						break;
					case "ORGANISATION_ADDRESS_LINE_4":
						type = ADDRESS_4;
						break;
					case "ORGANISATION_ADDRESS_LINE_5":
						type = ADDRESS_5;
						break;
					case "ORGANISATION_POSTCODE":
						postcodeCount++;
						type = POSTCODE;
						break;
					case "ORGANISATION_COUNTRY":
						type = COUNTRY;
						countryCount++;
						break;
					case "FREEDOM_OF_INFORMATION_FLAG":
						type = FOI;
						foiCount++;
						break;
					case "START_DATE_OF_REGISTRATION":
						type = START_DATE;
						startDateCount++;
						break;
					case "END_DATE_OF_REGISTRATION":
						type = END_DATE;
						endDateCount++;
						break;
					case "EXEMPT_PROCESSING_FLAG":
						type = EXEMPT_FLAG;
						exemptFlagCount++;
						break;
					case "TRADING_NAMES":
						type = TRADING_NAME;
						tradingNameCount++;
						break;
					case "CONTACT_IN_UK_C1":
						ukContactCount++;
						type = UK_CONTACT;
						break;
					case "SUBJECT_ACCESS_CONTACT_C2":
						type = SUBJECT_ACCESS_CONTACT;
						subjectAccessCount++;
						break;
					case "NATURE_OF_WORK_DESCRIPTION":
						natureOfWorkCount++;
						type = NATURE_OF_WORK;
						break;
					default:
						break;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					switch(qName.toUpperCase()){
						case "REGISTRATION":
							System.out.println(
									  "Records : " + recordCount
									+ "\nRegistration Numbers : " + regNumCount 
									+ "\nOrganisation Names : " + orgNameCount
									+ "\nCompanies House Numbers : " + companiesHouseCount
									+ "\nPostcodes : " + postcodeCount
									+ "\nCountries : " + countryCount
									+ "\nFOI Flags : " + foiCount
									+ "\nStart Dates : " + startDateCount
									+ "\nEnd Dates : " + endDateCount
									+ "\nExempt Flags : " + exemptFlagCount
									+ "\nTrading Names : " + tradingNameCount
									+ "\nUK Contact Flags : " + subjectAccessCount
									+ "\nNature of Works : " + natureOfWorkCount
									+ "\nOld Data Formats : " + oldBlobCount
									+ "\nNew Data Formats : " + newBlobCount);
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					switch (type) {
					case NATURE_OF_WORK:
						doStuff(new String(ch, start, length));
						type = 0;
						break;
					default:
						break;
					}
				}

				public void doStuff(String html) {
					Document doc = Jsoup.parse(html);
					Elements paras = doc.getElementsByTag("p");
					String heading = paras.get(0).text().split(" ")[0];
					if(heading.equals("Nature")){
						newBlobCount++;
					}else if(heading.equals("Purpose")){
						oldBlobCount++;
					}
				}
			};

			saxParser.parse("register08.2013.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
