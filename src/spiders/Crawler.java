/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import java.util.List;
import org.jsoup.nodes.Document;

/**
 *
 * @author luciano
 */
public abstract class Crawler{

    private List<String> links;
    private Document htmlDocument;
    
    public abstract boolean crawl(String url);

    public List<String> getLinks(){
        return links;
    }
    
    public Document getDocument(){
        return htmlDocument;
    }
}
