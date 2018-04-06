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
<<<<<<< HEAD
    private static final String FILE_PATH = "/home/luciano/Desktop/LUCIANO-UEFS/Java/Resources/doctors/indexedDoctors.map";
    private static int dif;    
=======
    private static final String FILE_PATH = "";
    
>>>>>>> b4aefbb12994c41b7aef0f5c78b4632588872491
    private DoctorCrawler spider = new DoctorCrawler();
    
    @Override
    public void run() {
<<<<<<< HEAD
        this.setUp();
        for(int i = 0; i < fileMap.size(); i++){
            try {
                spider.crawl(fileMap.getProperty(Integer.toString(i)));
                MyParser parser = new MyParser(spider.getDocument());                
                System.out.println(parser.parse().toString());
            } catch (InvalidTypeOfResponseException | FailedRequestException | EmptyDocumentFieldException ex) {
                Logger.getLogger(Experiment3.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NullPointerException ex){
                dif++;
            }            
        }
        System.out.println("Numero de paginas nao parsadas-> "+dif);
=======
        for(int i = 0; i < fileMap.size(); i++){
            try {
                spider.crawl(fileMap.getProperty(Integer.toString(i)));
                MyParser parser = new MyParser(spider.getDocument());
            } catch (InvalidTypeOfResponseException | FailedRequestException | EmptyDocumentFieldException ex) {
                Logger.getLogger(Experiment3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
>>>>>>> b4aefbb12994c41b7aef0f5c78b4632588872491
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
<<<<<<< HEAD
    
    public static void main(String[] args) throws InvalidTypeOfResponseException, FailedRequestException, EmptyDocumentFieldException{
//        DoctorCrawler crawler = new DoctorCrawler();
//        crawler.crawl("");
//        System.out.println(crawler.getDocument().getElementsByClass("doctorplacesaddress").select("a").attr("data-full-address"));
//        System.out.println(new MyParser(crawler.getDocument()).parse());
        Experiment3 exp = new Experiment3();
        exp.run();
    }
=======
>>>>>>> b4aefbb12994c41b7aef0f5c78b4632588872491
}
