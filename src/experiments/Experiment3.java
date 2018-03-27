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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import spiders.DoctorCrawler;
import util.parsers.MyParser;

/**
 *
 * @author luciano
 */
public class Experiment3 implements ICrawlExperiment{
    
    private Properties fileMap = new Properties();
    private static final String FILE_PATH = "";
    
    private DoctorCrawler spider = new DoctorCrawler();
    
    @Override
    public void run() {
        for(int i = 0; i < fileMap.size(); i++){
            try {
                spider.crawl(fileMap.getProperty(Integer.toString(i)));
                MyParser parser = new MyParser(spider.getDocument());
            } catch (InvalidTypeOfResponseException | FailedRequestException | EmptyDocumentFieldException ex) {
                Logger.getLogger(Experiment3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setUp(){
        try {
            fileMap.load(new FileInputStream(FILE_PATH));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Experiment3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Experiment3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
