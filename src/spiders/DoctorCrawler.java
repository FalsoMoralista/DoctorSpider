/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import exceptions.EmptyDocumentFieldException;
import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
import interfaces.ICrawlExperiment;
import interfaces.ICrawler;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.parsers.MyParser2;

/**
 *
 * @author luciano
 */
public class DoctorCrawler extends Crawler {

    private Document htmlDocument;
    private List<String> links = new LinkedList<>();
    private List<String> srcLinks;
    private String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private String startingUrl;

    public DoctorCrawler() {

    }

    public DoctorCrawler(String startingUrl) {
        this.startingUrl = startingUrl;
    }

    @Override
    public boolean crawl(String url) throws InvalidTypeOfResponseException, FailedRequestException {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).timeout(0); // try to establish a connection with the passed URL using the fake web browser(USER_AGENT)
            Document document = connection.get(); //  request its document(webpage)
            
            this.htmlDocument = document;
            if (connection.response().statusCode() == 200) { // code 200 for HTTP means OK(connection established)
                System.out.println("**Connected sucessfully**, web page received " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                throw new InvalidTypeOfResponseException();
            }
            Elements linksOnPage = document.select("a[href]");
//            System.out.println("Found " + "[" + linksOnPage.size() + "]" + " links on this page");
            linksOnPage.forEach(link ->{
                links.add(link.absUrl("href"));                
            });
            return true;
        } catch (IOException ex) {
            throw new FailedRequestException();
        } catch (IllegalArgumentException iae) {
            System.out.println("Error -> the response is something other than a page");
            return false;
        }        
    }

    @Override
    public List<String> getLinks() {
        return this.links;
    }

    public List<String> getSourceLinks() {
        return this.srcLinks;
    }

    public List<String> getParsedRelevantLinks() {
        List<String> list = new LinkedList<>();
        this.links.forEach(link ->{
        });
        return this.srcLinks;
    }
    
    private void generateSourceLinks(){        
    }

    @Override
    public Document getDocument() throws EmptyDocumentFieldException {
        if(this.htmlDocument == null){
            throw new EmptyDocumentFieldException();
        }
        return this.htmlDocument;
    }

    public void setUserAgent(String userAgent){
        this.USER_AGENT = userAgent;
    }
    
    public String getUserAgent(){
        return this.USER_AGENT;
    }
}
