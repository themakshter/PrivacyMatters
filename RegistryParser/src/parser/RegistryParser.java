package parser;
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

public class RegistryParser {
	public static void main(String[] args) {
		//RegistryParser.parse(args[0]);
		RegistryParser.parse("files/registryFiles/registry_example_8.xml");
	}
	
	public static void parse(String fileName){
		try {
			File file= new File(fileName);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new RegistryHandler();
			saxParser.parse(file, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
