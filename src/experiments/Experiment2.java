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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import spiders.DoctorCrawler;
import util.parsers.ParseEqualLinks;
import util.parsers.ParseEspecialities;

/**
 * Indexed all doctors by especiality.
 *
 * @author luciano
 */
public class Experiment2 implements ICrawlExperiment {

    private DoctorCrawler crawler = new DoctorCrawler();

    private static final String especialitiesPath = "/home/luciano/Desktop/LUCIANO-UEFS/Java/Spider/src/util/documents/especialities.map";
    private Properties especialitiesMap = new Properties();

    @Override
    public void run() {
        this.setUp();
        this.generateDirectoryMap("/home/luciano/Desktop/ic/Joao/Files/especialities", "fileMap.map");
//        for (int i = 0; i < especialitiesMap.size(); i++) {
//            ParseEqualLinks links = new ParseEqualLinks(especialitiesMap.getProperty(Integer.toString(i)));
//            List<String> documents = links.parse();
//            writeFile(documents,"/home/luciano/Desktop/LUCIANO-UEFS/Java/Spider/src/util/especialities/","especiality_"+i+".map");
//        }
    }

    private void setUp() {
        try {
            especialitiesMap.load(new FileInputStream(especialitiesPath));
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
            for (String especiality : map) {
                try {
                    writer.write(count++ + "=" + especiality + "\n");
                } catch (IOException ex) {
                    Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void writeFile(List<String> document, String path, String filename) {
        File f =  new File(path+filename);
        try {
            f.createNewFile();
            int count = 0;
            try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()))){
                System.out.println("Writing file...");
                for(String str : document){
                    writer.write(count+++"="+str+"\n");                
                }
                writer.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Experiment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generateDirectoryMap(String dir, String filename){
        File file = new File(dir);
        System.out.println(Arrays.toString(file.listFiles()));
    }
    
    public static void main(String[] args) {
        Experiment2 exp = new Experiment2();
        exp.run();
    }
}
