import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class RegistrySAXParser {
	public static void main(String argv[]) {
		 
	    try {
	 
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
	 
		DefaultHandler handler = new DefaultHandler() {
		private int type = 0;
		private static final int FIRST_NAME = -1;
		private static final int LAST_NAME = -2;
		private static final int NICKNAME = -3;
		private static final int SALARY = -4;
		private static final int REGISTRATION_NUMBER = 1;
		private static final int ORGANISATION_NAME = 2;
		private static final int ADDRESS_1 = 3;
		private static final int ADDRESS_2 = 4;
		private static final int ADDRESS_3 = 5;
		private static final int ADDRESS_4 = 6;
		private static final int ADDRESS_5 = 7;
		private static final int POSTCODE = 8;
		private static final int COUNTRY = 9;
		private static final int FOI = 10;
		private static final int START_DATE = 11;
		private static final int END_DATE = 12;
		private static final int EXEMPT_FLAG = 13;
		private static final int UK_CONTACT = 14;
		private static final int SUBJECT_ACCESS_CONTACT = 15;
		private static final int NATURE_OF_WORK = 16;
		private String address = "\tAddress : ";
		
		public void startElement(String uri, String localName,String qName,Attributes attributes) throws SAXException {
	 
			//System.out.println("Start Element :" + qName);
			
			switch(qName.toUpperCase()){
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
				case "REGISTRATION_NUMBER":
					type = REGISTRATION_NUMBER;
					break;
				case "ORGANISATION_NAME":
					type = ORGANISATION_NAME;
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
				case "CONTACT_IN_UK_C1":
					type = UK_CONTACT;
					break;
				case "SUBJECT_ACCESS_CONTACT_C2":
					type = SUBJECT_ACCESS_CONTACT;
					break;
				case "NATURE_OF_WORK_DESCRIPTION":
					type = NATURE_OF_WORK;
					break;
				default:
					break;
			}
			
	
		}
	 
		public void endElement(String uri, String localName,
			String qName) throws SAXException {
		}
	 
		public void characters(char ch[], int start, int length) throws SAXException {
			switch(type){
			case FIRST_NAME:
				System.out.println("Employee\n\tFirst Name : " + new String(ch, start, length));
				type = 0;
				break;
			case LAST_NAME:
				System.out.println("\tLast Name : " + new String(ch, start, length));
				type = 0;
				break;
			case NICKNAME:
				System.out.println("\tNick Name : " + new String(ch, start, length));
				type = 0;
				break;
			case SALARY:
				System.out.println("\tSalary : " + new String(ch, start, length));
				type = 0;
				break;
			case REGISTRATION_NUMBER:
				System.out.println("Organisation\n\tRegistration Number : " + new String(ch, start, length));
				type = 0;
				break;
			case ORGANISATION_NAME:
				System.out.println("\tName : " + new String(ch, start, length));
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
				System.out.println(address);
				address = "\tAddress : ";
				type = 0;
				break;
			case POSTCODE:
				System.out.println("\tPostcode : " + new String(ch, start, length));
				type = 0;
				break;
			case COUNTRY:
				System.out.println("\tCountry : " + new String(ch, start, length));
				type = 0;
				break;
			case FOI:
				System.out.println("\tFreedom of Information : " + new String(ch, start, length));
				type = 0;
				break;
			case START_DATE:
				System.out.println("\tStart of Registration : " + new String(ch, start, length));
				type = 0;
				break;
			case END_DATE:
				System.out.println("\tEnd of Registration : " + new String(ch, start, length));
				type = 0;
				break;
			case EXEMPT_FLAG:
				System.out.println("\tExempt from Processing : " + new String(ch, start, length));
				type = 0;
				break;
			case UK_CONTACT:
				System.out.println("\tContact in UK: " + new String(ch, start, length));
				type = 0;
				break;
			case SUBJECT_ACCESS_CONTACT:
				System.out.println("\tSubject Access Contact : " + new String(ch, start, length));
				type = 0;
				break;
			case NATURE_OF_WORK:
				System.out.println("\tNature of Work : " + new String(ch, start, length));
				type = 0;
				break;
			default:
				break;
			}
		}
	 
	     };
	 
	       saxParser.parse("registry_example_2.xml", handler);
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	 
	   }
}
