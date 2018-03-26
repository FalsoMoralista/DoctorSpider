/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import interfaces.IParser;
import java.util.List;

/**
 *
 * @author luciano
 */
public class MyParser implements IParser {

    private static Document doc;

    public MyParser(Document doc) {
        this.doc = doc;
    }
    
    /**
     *  Parse info 
     */
    public void parse2() {
        
        Elements elements = doc.getElementsByClass("title").select("h1");
        System.out.println(elements.text());

        elements = doc.getElementsByClass("title").select("p");
        System.out.println(elements.first().text());

        elements = doc.getElementsByClass("regnum");
        String s = elements.select("p").last().text();
        System.out.println(s.substring(s.indexOf(":") + 2, s.length()));

        elements = doc.getElementsByClass("subspecialities");
        System.out.println(elements.text());

        elements = doc.getElementsByClass("insurances");
        for (Element e : elements) {
            System.out.println(e.text());
        }

        elements = doc.getElementsByClass("address");
        for (Element e : elements) {
            System.out.println(e.text().substring(0, e.text().indexOf("|")));
            System.out.println("lat= " + e.select("a").attr("data-lat"));
            System.out.println("long= " + e.select("a").attr("data-lng"));
            System.out.println("city code= " + e.select("a").attr("data-city-key"));
            System.out.println("");
        }
    }

    @Override
    public List<String> parse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
