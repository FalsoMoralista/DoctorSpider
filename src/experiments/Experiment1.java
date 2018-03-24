/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import interfaces.ICrawlExperiment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.select.Elements;
import spiders.DoctorCrawler;
import util.MyParser2;

/**
 *
 * @author luciano
 */
public class Experiment1 implements ICrawlExperiment {

    private static final String mapPath = "/home/luciano/Desktop/LUCIANO-UEFS/Java/Spider/src/util/queryMap.map";

    private Properties map = new Properties();

    private DoctorCrawler crawler = new DoctorCrawler();

    private List<String> sourceLinks;

    @Override
    public void run() {
//        long time = System.currentTimeMillis();        
//        System.out.println("Tempo total:\t\t" + String.format("%.2f", (System.currentTimeMillis() - time) / 1000.0 / 3600.0));;
        setUp();        
        for(int i = 0; i < map.size(); i++){
            crawler.crawl(map.getProperty(Integer.toString(i)));
            MyParser2 parser = new MyParser2(crawler.getDocument());
            System.out.println(parser.toString());
        }
    }

    private void setUp() {
        try {
            map.load(new FileInputStream(mapPath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Used to create an specific map from a document.
     */
    private void createDocumentMap(String mapPath) {
        String url = null; // fix later (get each url from the map)
        String tmpUrl = url;
        List<String> srcLinks = new LinkedList();
        int counter = 1;
        while (crawler.crawl(tmpUrl)) {
            srcLinks.add(tmpUrl);
            tmpUrl = url + "/" + Integer.toString(counter);
            counter++;
        }
        String filename = "queryMap.map";
        File f = new File(mapPath + filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
                Path path = Paths.get(f.getPath());
                int count = 0;
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    for (String s : srcLinks) {
                        writer.write(count++ + "=" + s + "\n");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        Experiment1 experiment = new Experiment1();
        experiment.run();
    }
}
