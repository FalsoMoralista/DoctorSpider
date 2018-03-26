/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import exceptions.EmptyDocumentFieldException;
import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
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
import util.parsers.MyParser2;

/**
 * Used to create a document map, then a query map. The query map will be
 * created from the parsed relevant links contained on the document map. 
 * 
 * The site used to collect the data actually dispose from a total of 424928 
 * objects in it's server database. The source web page that links to those 
 * objects references approximately 4007 objects distributed over in 200 links 
 * (web pages).
 * 
 * The problem is that the source page only references 4007 objects distrubuted
 * at other 200 web pages (approximately 20 relevant objects per page), but each 
 * time that a connection is established with one of this 200 web pages, 
 * different relevant links to the objects are retrieved, then only will is 
 * necessary to make millions of connections to the web page until all the 
 * combinations are reached. 
 * 
 * But the other problem is: the first 4007 links are indexed in about 2 minutes
 * and then this amount decrease to 2 links sometimes even 0 links are found in
 * the same 2 minutes.
 * 
 * @author luciano
 */
public class Experiment1 implements ICrawlExperiment {

    private static final String mapPath = "/home/luciano/Desktop/LUCIANO-UEFS/Java/Spider/src/util/documents/documentMap.map";
    private static final String queryPath = "/home/luciano/Desktop/LUCIANO-UEFS/Java/Spider/src/util/queries/";

    private static final int LINKS = 424928;

    private Properties map = new Properties();

    private DoctorCrawler crawler = new DoctorCrawler();

    private List<String> sourceLinks;

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        setUp();
        List<String> relevantLinks = new LinkedList();
        try {
            while (relevantLinks.size() < LINKS) {
                for (int i = 0; i < map.size(); i++) {
                    int count = relevantLinks.size();
                    List<String> parsedLinks = new LinkedList();
                    crawler.crawl(map.getProperty(Integer.toString(i)));
                    MyParser2 parser = new MyParser2(crawler.getDocument());
                    parsedLinks.addAll(parser.parse());
                    for (String link : parsedLinks) {
                        if (!relevantLinks.contains(link)) {
                            relevantLinks.add(link);
                        }
                    }
                }
                System.out.println("Total" + "[" + relevantLinks.size() + "/" + LINKS + "]");
            }
            this.createQueryMap(queryPath, "queryMap.map", relevantLinks);
        } catch (Exception ex) {
            this.createQueryMap(queryPath, "queryMap.map", relevantLinks);
        }
        System.out.println("Tempo total:\t\t" + String.format("%.2f", (System.currentTimeMillis() - time) / 1000.0 / 3600.0));;
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
        try {
            while (crawler.crawl(tmpUrl)) {
                srcLinks.add(tmpUrl);
                tmpUrl = url + "/" + Integer.toString(counter);
                counter++;
            }
        } catch (InvalidTypeOfResponseException ex) {
            Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailedRequestException ex) {
            Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
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

    private void createQueryMap(String path, String filename, List<String> list) {
        File file = new File(path + filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Path p = Paths.get(file.getPath());
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            System.out.println("**Writing file**");
            int count = 0;
            for (String l : list) {
                writer.write(count++ + "=" + l + "\n");
            }
            System.out.println("done");
        } catch (IOException ex) {
            Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crawlMap(Properties map) {
        List<String> parsedLinks = new LinkedList();
        for (int i = 0; i < map.size(); i++) {
            try {
                try {
                    crawler.crawl(map.getProperty(Integer.toString(i)));
                } catch (InvalidTypeOfResponseException ex) {
                    Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FailedRequestException ex) {
                    Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
                }
                MyParser2 parser = new MyParser2(crawler.getDocument());
                parsedLinks.addAll(parser.parse());
            } catch (EmptyDocumentFieldException ex) {
                Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        Experiment1 experiment = new Experiment1();
        experiment.run();
    }
}
