/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import org.jsoup.nodes.Document;

/**
 *
 * @author luciano
 */
public interface ICrawler {
    public boolean crawl(String url);
    public List <String> getLinks();
    public Document getDocument();
}
