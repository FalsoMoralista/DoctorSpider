/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import exceptions.EmptyDocumentFieldException;
import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luciano
 */
public class Spider {

    private static final int TO_SEARCH = 100; // amount of pages
    private Set<String> visited = new HashSet<>(); // already visited
    private List<String> toVisit = new LinkedList<>(); // to visit

    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = toVisit.remove(0);
        } while (visited.contains(nextUrl));
        visited.add(nextUrl);
        return nextUrl;
    }
    
    /**
     * Search from a word starting from an given URL
     * @param url
     * @param word
     */
    public void search(String url, String word){
        while(this.visited.size() < TO_SEARCH){ try {
            // while the determinated amount of sites has not been visited
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if(this.toVisit.isEmpty()){ // if there's no url, start from this
                currentUrl = url;
                this.visited.add(url);
            }else{
                currentUrl = this.nextUrl(); // otherwise, get the next url in list
            }            
            leg.crawl(currentUrl);
            boolean op = leg.searchWord(word);
            if(op){ // if found
                System.out.println("Word found: "+word+" at "+currentUrl);
                break;
            }
            toVisit.addAll(leg.getLinks()); //  save all visited links
            } catch (InvalidTypeOfResponseException ex) {
                Logger.getLogger(Spider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FailedRequestException ex) {
                Logger.getLogger(Spider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EmptyDocumentFieldException ex) {
                Logger.getLogger(Spider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Done");
        System.out.println("Page(s) visited: "+visited.size());
    }
}
