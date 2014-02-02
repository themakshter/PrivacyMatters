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
				private int recordCount = 0;
				private int natureOfWorkCount = 0;
				private int type = 0;
				private static final int FIRST_NAME = -1;
				private static final int LAST_NAME = -2;
				private static final int NICKNAME = -3;
				private static final int SALARY = -4;
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

					// System.out.println("Start Element :" + qName);

					switch (qName.toUpperCase()) {
					case "FIRSTNAME":
						type = FIRST_NAME;
						break;
					case "LASTNAME":
						type = LAST_NAME;
						break;
					case "NICKNAME":
						type = NICKNAME;
						break;
					case "SALARY":
						type = SALARY;
						break;
					case "REGISTRATION":
						type = REGISTRATION;
						break;
					case "RECORD":
						type = RECORD;
						recordCount++;
						break;
					case "REGISTRATION_NUMBER":
						type = REGISTRATION_NUMBER;
						break;
					case "ORGANISATION_NAME":
						type = ORGANISATION_NAME;
						break;
					case "COMPANIES_HOUSE_NUMBER":
						type = COMPANIES_HOUSE_NUMBER;
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
						type = POSTCODE;
						break;
					case "ORGANISATION_COUNTRY":
						type = COUNTRY;
						break;
					case "FREEDOM_OF_INFORMATION_FLAG":
						type = FOI;
						break;
					case "START_DATE_OF_REGISTRATION":
						type = START_DATE;
						break;
					case "END_DATE_OF_REGISTRATION":
						type = END_DATE;
						break;
					case "EXEMPT_PROCESSING_FLAG":
						type = EXEMPT_FLAG;
						break;
					case "TRADING_NAMES":
						type = TRADING_NAME;
						break;
					case "CONTACT_IN_UK_C1":
						type = UK_CONTACT;
						break;
					case "SUBJECT_ACCESS_CONTACT_C2":
						type = SUBJECT_ACCESS_CONTACT;
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
							//System.out.println("Records : " + recordCount);
							//System.out.println("Nature of Works : " + natureOfWorkCount);
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					switch (type) {
					case FIRST_NAME:
						System.out.println("Employee\n\tFirst Name : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case LAST_NAME:
						System.out.println("\tLast Name : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case NICKNAME:
						System.out.println("\tNick Name : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case SALARY:
						System.out.println("\tSalary : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case REGISTRATION_NUMBER:
						System.out
								.println("Organisation\n\tRegistration Number : "
										+ new String(ch, start, length));
						type = 0;
						break;
					case ORGANISATION_NAME:
						System.out.println("\tName : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case COMPANIES_HOUSE_NUMBER:
						System.out.println("\tCompanies House Number : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case ADDRESS_1:
						address += new String(ch, start, length);
						type = 0;
						break;
					case ADDRESS_2:
						address += ", " + new String(ch, start, length);
						type = 0;
						break;
					case ADDRESS_3:
						address += ", " + new String(ch, start, length);
						type = 0;
						break;
					case ADDRESS_4:
						address += ", " + new String(ch, start, length);
						type = 0;
						break;
					case ADDRESS_5:
						address += ", " + new String(ch, start, length);
						type = 0;
						break;
					case POSTCODE:
						System.out.println(address);
						address = "\tAddress : ";
						System.out.println("\tPostcode : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case COUNTRY:
						System.out.println("\tCountry : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case FOI:
						System.out.println("\tFreedom of Information : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case START_DATE:
						System.out.println("\tStart of Registration : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case END_DATE:
						System.out.println("\tEnd of Registration : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case EXEMPT_FLAG:
						System.out.println("\tExempt from Processing : "
								+ new String(ch, start, length));
						type = 0;
						break;
					case TRADING_NAME:
						System.out.println("\tTrading names : " + new String(ch, start, length));
						type = 0;
						break;
					case UK_CONTACT:
						System.out.println("\tContact in UK: "
								+ new String(ch, start, length));
						type = 0;
						break;
					case SUBJECT_ACCESS_CONTACT:
						System.out.println("\tSubject Access Contact : "
								+ new String(ch, start, length));
						type = 0;
						break;
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
					Elements lists = doc.getElementsByTag("ul");
					Elements headings = doc.getElementsByTag("b");
					Document doc2;
					if (headings.get(0).text().substring(0, 6).equals("Nature")) {
						String[] nature_of_work = paras.get(0).text().split("-");
						System.out.println("\t" +nature_of_work[0] + " : " + nature_of_work[1]);
						String[] reasons = paras.get(4).text().split(" ");
						String reasons_for_processing = "\t" + headings.get(2).text() + " :";
						for (int i = 4; i < reasons.length; i++) {
							reasons_for_processing += " " + reasons[i];
						}
						System.out.println(reasons_for_processing);
						String processed = "\t" + headings.get(3).text() +  " :";
						doc2 = Jsoup.parse(lists.get(0).html());
						Elements info_collected = doc2.getElementsByTag("li");
						for (int i = 0; i < info_collected.size(); i++) {
							processed += " " + info_collected.get(i).text() + ",";
						}
						doc2 = Jsoup.parse(lists.get(1).html());
						Elements sensitive_info = doc2.getElementsByTag("li");
						for (int i = 0; i < sensitive_info.size(); i++) {
							processed += " " + sensitive_info.get(i).text()
									+ "[SENSITIVE],";
						}
						processed = processed.substring(0, processed.length() - 1);
						System.out.println(processed);
						String shared_with = "\t" + headings.get(5).text() + " :";
						doc2 = Jsoup.parse(lists.get(2).html());
						Elements shared = doc2.getElementsByTag("li");
						for (int i = 0; i < shared.size(); i++) {
							shared_with += " " + shared.get(i).text() + ",";
						}
						shared_with = shared_with.substring(0, shared_with.length() - 1);
						System.out.println(shared_with);
						System.out.println("\t" + headings.get(8).text() + " : "
								+ paras.get(11).text());
					}
				}
			};

			saxParser.parse("registry_example_1.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
