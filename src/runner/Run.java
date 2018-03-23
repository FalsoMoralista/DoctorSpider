/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.SpiderLeg;
import util.MyParser;

/**
 * TODO: create mechanism to read a configuration file before run the experiment
 *
 * @author luciano
 */
public class Run {

    private static final String url = "https://www.doctoralia.com.br/medico/simarro+rios+jose+eduardo-12784612";
    private static final String url2 = "https://www.doctoralia.com.br/medico/rodrigues+luciano-14806720";

    public static void main(String[] args) {
        SpiderLeg leg = new SpiderLeg();

        long time = System.currentTimeMillis();

        leg.crawl(url2);
        Document doc = leg.getDocument();
        MyParser parser = new MyParser(doc);
        parser.collectInfo();
        System.out.println("Tempo total:\t\t" + String.format("%.2f", (System.currentTimeMillis() - time) / 1000.0 / 3600.0));;
    }
}
