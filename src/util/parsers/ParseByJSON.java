/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.parsers;

import exceptions.EmptyDocumentFieldException;
import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
import interfaces.IParser;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.jsoup.nodes.Document;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spiders.DoctorCrawler;

/**
 *
 * @author luciano
 */
public class ParseByJSON implements IParser {

    private Document doc;

    public ParseByJSON(Document doc) {
        this.doc = doc;
    }

    public String parseBy() {
        JSONObject obj = new JSONObject();
        for (Element e : doc.select("address")) {
            try {
                obj.put("address", e.text());
            } catch (JSONException ex) {
                Logger.getLogger(ParseByJSON.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj.toString();
    }

    @Override
    public List<String> parse() {
        List<String> parsed = new LinkedList<>();
        Elements elements = doc.getElementsByTag("h1");
        parsed.add(elements.toString());
        elements = doc.getElementsByTag("p");
        parsed.add(elements.toString());
        return parsed;
    }

    public static void main(String[] args) throws InvalidTypeOfResponseException, FailedRequestException, EmptyDocumentFieldException {
        DoctorCrawler crawler = new DoctorCrawler();
        crawler.crawl("https://www.doctoralia.com.br/");
        Document doc = crawler.getDocument();
        System.out.println(doc.getElementsByTag("form").forms().toString());
        
    }
}
