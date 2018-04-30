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
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import spiders.DoctorCrawler;
import util.parsers.MyParser;
import util.parsers.MyParser2;

/**
 * Indexed all doctors by specialty.
 *
 * @author luciano
 */
public class Experiment2 implements ICrawlExperiment {

    private DoctorCrawler crawler = new DoctorCrawler();

    private static final String specialtiesMapPath = "/home/luciano/Desktop/Joao/Files/especialities/fileMap.map";
    private Properties fileMap = new Properties();

    private static final String outputPath = "/home/luciano/Desktop/Joao/Files/doctors/";
    private static int count;
    
    @Override
    public void run() {
        this.setUp();
        for (int i = 37; i < fileMap.size(); i++) {
            List<String> doctors = new LinkedList();
            try {
                Properties specialty = new Properties();// this will be used to load each specialty
                specialty.load(new FileInputStream(fileMap.getProperty(Integer.toString(i)))); // load a map of specialties
                for (int j = 0; j < specialty.size(); j++) { // this will be used to get all the lines from a specialty map
                    crawler.crawl(specialty.getProperty(Integer.toString(j))); // crawl a link  
                    MyParser2 parseAllRelevantLinks = new MyParser2(crawler.getDocument()); // get it's document
                    List<String> relevantLinks = parseAllRelevantLinks.parse(); // get all relevant(parsed) links
                    doctors.addAll(relevantLinks);// save it on a list
                }
                System.out.println("found ->"+doctors.size()+"links");
                indexAllDoctors(outputPath, "indexedDoctors.map", doctors);// then append it to a file
                System.out.println("** done **"+" ["+i+"/"+fileMap.size()+"]");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InvalidTypeOfResponseException | FailedRequestException | EmptyDocumentFieldException ex) {
                Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setUp() {
        try {
            fileMap.load(new FileInputStream(specialtiesMapPath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateEspecialitiesMap(String path, String filename, List<String> map) throws IOException {
        File file = new File(path + filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        Path p = Paths.get(file.getPath());
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            int count = 0;
            for (String specialty : map) {
                try {
                    writer.write(count++ + "=" + specialty + "\n");
                } catch (IOException ex) {
                    Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void writeFile(List<String> document, String path, String filename) {
        File f = new File(path + filename);
        try {
            f.createNewFile();
            int count = 0;
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()))) {
                System.out.println("Writing file...");
                for (String str : document) {
                    writer.write(count++ + "=" + str + "\n");
                }
                writer.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   private static void generateDirectoryMap(String dir, String filename) {
        try {
            File[] files = new File(dir).listFiles();
            File f = new File(dir + filename);
            if (!f.exists()) {
                f.createNewFile();
                Arrays.sort(files);
                try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(dir + filename))) {
                    for (int i = 0; i < files.length; i++) {
                        writer.write(i + "=" + files[i] + "\n");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void indexAllDoctors(String outputPath, String filename, List<String> doctors) {
        File f = new File(outputPath + filename);
        Properties map = new Properties();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            map.load(new FileInputStream(f.getPath()));
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()),APPEND)) {
                int count = map.size();
                for (String doc : doctors) {
                    writer.write(count++ + "=" + doc);
                    writer.newLine();
                }
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        Experiment2 exp = new Experiment2();
        exp.run();
    }
}
