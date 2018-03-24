/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import java.util.LinkedList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spiders.SpiderLeg;
import util.MyParser;

/**
 * TODO: create mechanism to read a configuration file before run the experiment
 *
 * @author luciano
 */
public class Run {

    private static final String url = "";
    private static final String url2 = "";
    private static final String url3 = "";

    public static void main(String[] args) {
        SpiderLeg leg = new SpiderLeg();
//        experiment();
        getRelevantLinks(url3);
        long time = System.currentTimeMillis();
//        leg.crawl(url2);
//        Document doc = leg.getDocument();
//        MyParser parser = new MyParser(doc);
//        parser.parse();
        System.out.println("Tempo total:\t\t" + String.format("%.2f", (System.currentTimeMillis() - time) / 1000.0 / 3600.0));;
    }
    
    public static void experiment(){
        SpiderLeg leg = new SpiderLeg();
        leg.crawl(url3);
        List<String> links = leg.getLinks();
        List<Document> documents = new LinkedList();
        System.out.println(links.size());
        links.forEach(link ->{
            System.out.println(link);
//            leg.crawl(link);
//            documents.add(leg.getDocument());
        });
    }
    
    public static List getRelevantLinks(String startingUrl){
        String url = startingUrl;
        SpiderLeg leg = new SpiderLeg();
        List relevantLinks = new LinkedList();
        int counter = 1;
        while(leg.crawl(url)){
            relevantLinks.add(url);
            url = startingUrl+"/"+Integer.toString(counter);           
            System.out.println(url);
            counter++;
        }
        return relevantLinks;
    }
}
