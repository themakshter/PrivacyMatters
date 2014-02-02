import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class x {
	public static void main(String[] args) throws IOException {
		File input = new File("nature_of_work_description_1.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com./");
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
}
