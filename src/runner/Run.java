/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import spider.Spider;
import spider.SpiderLeg;

/**
 * TODO: create mechanism to read a configuration file before run the experiment
 *
 * @author luciano
 */
public class Run {
    private static final String url = "https://www.doctoralia.com.br/medico/junqueira+guimaraes+luciano-11909153";
    private static final String url2 = "https://www.doctoralia.com.br/medico/rodrigues+luciano-14806720";
    
    public static void main(String[] args) throws JSONException {
        SpiderLeg leg = new SpiderLeg();
        leg.crawl(url2);
        Document doc = leg.getDocument();
        JSONObject json = new JSONObject(doc.head().toString());
        long time = System.currentTimeMillis();
        System.out.println("Tempo total:\t\t" + String.format("%.2f", (System.currentTimeMillis() - time) / 1000.0 / 3600.0));
    }
}
