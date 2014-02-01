import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class x {
	public static void main(String[] args) throws IOException {
		File input = new File("nature_of_work_description.html");
		Document doc = Jsoup.parse(input,"UTF-8","http://example.com./");
		Elements x = doc.getElementsContainingOwnText("Nature");		
		Element body = doc.body();
		//System.out.println(x.text());
		Elements y = doc.getElementsByIndexEquals(1);
		System.out.println(y.text());
	}
}
