/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author luciano
 */
public class Spider {

    private static final int TO_SEARCH = 10; // amount of pages
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
    
    public void search(String url, String word){
        while(this.toVisit.size() < TO_SEARCH){
            String currentUrl;
            Leg leg = new Leg();
            if(this.toVisit.isEmpty()){
                currentUrl = url;
                this.visited.add(url);
            }else{
                currentUrl = this.nextUrl();    
            }            
            leg.crawl(currentUrl);
            boolean op = leg.searchWord(word);
            if(op){
                System.out.println("Word found: "+word+" at "+currentUrl);
                break;
            }
            visited.addAll(leg.getLinks());
        }
        System.out.println("Done");
        System.out.println("Page(s) visited: "+visited.size());
    }
}
