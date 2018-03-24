/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luciano
 */
public class SpiderLeg extends Crawler{
    
    private List<String> links = new LinkedList<>(); // list of URLs
    private Document htmlDocument; //  the webpage
    // use a fake agent so the web server thinks its a normal browser.
    private static final String USER_AGENT =  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1"; 
    
    /**
     *  Makes a HTTP request, gets the document and its links then 
     * @param url
     * @return 
     */
    @Override
    public boolean crawl(String url){
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT); // try to establish a connection with the passed URL using the fake web browser(USER_AGENT)
            Document document = connection.get(); //  request its document(webpage)
            this.htmlDocument = document;
            if(connection.response().statusCode() == 200){ // code 200 for HTTP means OK(connection established)
                System.out.println("**Connected sucessfully**, web page received "+url);
            }if(!connection.response().contentType().contains("text/html")){
                System.out.println("Error -> received something that isn't HTML");
                return false;
            }
            Elements linksOnPage = document.select("a[href]");
            System.out.println("Found "+"["+linksOnPage.size()+"]"+" links on this page");
            for(Element link : linksOnPage){
                links.add(link.absUrl("href"));
            }
            return true;
        } catch (IOException ex) {
            System.out.println("Error -> the request failed");
            return false;
        }catch(IllegalArgumentException iae){
            System.out.println("Error -> the response is something other than a page");
            return false;
        }
    }
    
    public boolean searchWord(String word){

        if(this.htmlDocument == null){
            System.out.println("Error -> empty document, certify the method Crawl() has been called");
            return false;
        }
        System.out.println("Searching for the word "+ word+" ...");
        String bodyText = this.htmlDocument.body().text(); // gets the document body text
        return bodyText.toLowerCase().contains(word.toLowerCase()); // check if it contains the desired word
    }
    
    @Override
    public List<String> getLinks(){
        return this.links;
    }    
    
<<<<<<< HEAD:src/spiders/SpiderLeg.java
    /**
     *
     * @return
     */
    @Override
=======
>>>>>>> aff59b6c94fb453d717a566908a536dafc2119df:src/spider/SpiderLeg.java
    public Document getDocument(){
        return this.htmlDocument;
    }
}
