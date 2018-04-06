/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.parsers;

import interfaces.IParser;
import java.util.LinkedList;
import java.util.List;
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
    public List<String> parse() {
        List<String> list = new LinkedList();
          Elements elements = doc.getElementsByClass("photo").select("a");
          elements.forEach(e ->{              
              if(!e.attr("href").contains("centro-medico")){
                  list.add(e.attr("href"));                  
              }
          });
          return list;
    }    
}
