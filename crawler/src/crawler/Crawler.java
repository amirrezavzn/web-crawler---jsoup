
package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author Rsa.co
 */
public class Crawler {
     private static final int MaxPageNum = 1100;
    private static HashSet<String> links;

    public Crawler() {
        links = new HashSet<>();   // links set (duplicates_elimination)
     
    }

    public void getPageLinks(String URL) {   //builds a set of links (size = maxPageNum)
        if ((!links.contains(URL))  && links.size()<MaxPageNum  ) {

            try {
                links.add(URL);
                Document doc = Jsoup.connect(URL).get();  //makes a doc of webpage
                Elements linksOnPage = doc.select("h3.title > a[href] ,.pagination li a[href]");  //all links on the current page
        
                for (Element page : linksOnPage) {
                    if((links.size()<MaxPageNum)&& (!links.contains(page.attr("abs:href"))))
                    getPageLinks(page.attr("abs:href"));  //crawls the links on the page
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
    
   public static void anqezi(HashSet<String> urls) {
        FileCrawl boz = new FileCrawl();
   for (String sogol : urls){
       try {
                Document document = Jsoup.connect(sogol).get();
                Elements t = document.select("title");
                Elements el = document.select(".content p");
               
                boz.FileCrawl1(t.text() ,el.toString().replaceAll("<p dir=\"rtl\">", "").replaceAll("</p>", "").replaceAll("<p dir=\"rtl\" style=\"text-align:justify;\">", "").replaceAll("<span>", "").replaceAll("/span>", "").replaceAll("<span style=\"text-align:justify;\">", "") , sogol);
                System.out.println(t.text());
                System.out.println(el.toString().replaceAll("<p dir=\"rtl\">", "").replaceAll("</p>", ""));
                 } catch (IOException e) {
                System.err.println("For '" + sogol + "': " + e.getMessage());
            }
   }
   } 
   
   public HashSet<String> getlinks(){
         return links;
   }
}
