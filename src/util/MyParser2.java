/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import interfaces.IParser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author luciano
 */
public class MyParser2 implements IParser{
    
    private Document doc;
    
    public MyParser2(Document doc){
        this.doc = doc;
    }
    
    @Override
    public void parse() {
          Elements elements = doc.getElementsByClass("media doctor").select("a");
          System.out.println(elements.attr("href"));
    }

    //fix this
    @Override
    public String toString(){
        Elements elements = doc.getElementsByClass("media doctor").select("a");          
        return elements.attr("href");
    }
    
}
