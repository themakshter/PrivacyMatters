import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class x {
	public static void main(String[] args) throws IOException {
		File input = new File("nature_of_work_description_1.html");
		Document doc = Jsoup.parse(input,"UTF-8","http://example.com./");
		Elements x = doc.getElementsContainingOwnText("We process");		
		Element body = doc.body();
		Elements y = doc.getElementsByTag("p");
		Elements z = doc.getElementsByTag("ul");
		Document doc2 = Jsoup.parse(z.get(0).html());
		Elements za = doc2.getElementsByTag("li");
		//System.out.println(y.get(11).text());
		System.out.println(z.get(0));
		System.out.println(za.size());
	}
}
