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
import java.util.LinkedList;
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

    @Override
    public List<String> parse() {
        List<String> attrs = new LinkedList();
        Elements elements = doc.getElementsByClass("title").select("h1");
        elements.forEach(e -> attrs.add(e.text() + " "));

        elements = doc.getElementsByClass("title").select("p");
        attrs.add(elements.first().text() + " ");

        elements = doc.getElementsByClass("regnum");
        String s = elements.select("p").last().text();
        attrs.add(s.substring(s.indexOf(":") + 2, s.length()) + " ");

        elements = doc.getElementsByClass("subspecialities");
        attrs.add(elements.text() + " ");

        elements = doc.getElementsByClass("insurances");
        for (Element e : elements) {
            attrs.add(e.text() + " ");
        }

        elements = doc.getElementsByClass("address");
        for (Element e : elements) {
            attrs.add(e.text());
            attrs.add("lat= " + e.select("a").attr("data-lat") + " ");
            attrs.add("long= " + e.select("a").attr("data-lng") + " ");
            attrs.add("city code= " + e.select("a").attr("data-city-key") + " ");
        }
        return attrs;
    }
}
