/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import exceptions.EmptyDocumentFieldException;
import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Doctor;
import org.json.JSONException;
import spiders.DoctorCrawler;
import util.parsers.MyParser;

/**
 *
 * @author Luciano Araujo Dourado Filho
 */
public class DoctorParser {

    private Properties links = new Properties();

    private final String DATA_DIRECTORY;

    private DoctorCrawler spider = new DoctorCrawler();

    private HashMap<Integer, Doctor> doctors = new HashMap<>();

    public DoctorParser() throws FileNotFoundException, IOException {

        Properties properties = new Properties();

        properties.load(new FileInputStream(new File("src/resources/run.properties")));

        DATA_DIRECTORY = properties.getProperty("DATA_DIRECTORY");

        this.setUp(); // carrega os links
    }

    /**
     * Itera cada link fazendo o parse dos medicos e inserindo-os numa tabela
     *
     * @throws java.io.IOException
     * @throws exceptions.InvalidTypeOfResponseException
     * @throws exceptions.FailedRequestException
     * @throws exceptions.EmptyDocumentFieldException
     */
    public void start(int point) throws IOException, InvalidTypeOfResponseException, FailedRequestException, EmptyDocumentFieldException, JSONException {

        MyParser parser = new MyParser(null); // instancia o parser

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File outFile = new File("doctors.txt");

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile.getPath()), APPEND);

        for (int i = point; i < links.size(); i++) {
            try {

                String link = links.getProperty(Integer.toString(i));

                spider.crawl(link);

                parser.setDocument(spider.getDocument());

                Doctor doc = parser.getDoctor();

                StringWriter stringEmp = new StringWriter();

                objectMapper.writeValue(stringEmp, doc);

                save(stringEmp.toString(), writer);

                System.out.println("Feito, total: " + (i + 1) + "/" + links.size());

            } catch (FailedRequestException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }

    public void listDoctors() throws IOException {

        File f = new File("doctors.txt");

        List<String> list = Files.readAllLines(Paths.get(f.getPath()));

        list.forEach(System.out::println);

    }

    private void save(String info, BufferedWriter writer) throws IOException {

        writer.write(info);

        writer.flush();

        writer.newLine();
    }

    private void setUp() {
        try {
            links.load(new FileInputStream(DATA_DIRECTORY));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save() {

        FileOutputStream stream = null;
        try {
            File f = new File("doctorHashMap.bin");
            stream = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(this.doctors);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void load() {
        try {
            FileInputStream instream = null;
            File f = new File("doctorHashMap.bin");
            if (f.exists()) {
                try {
                    instream = new FileInputStream(f);
                    ObjectInputStream ois = new ObjectInputStream(instream);
                    this.doctors = (HashMap<Integer, Doctor>) ois.readObject();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        instream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DoctorParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws InvalidTypeOfResponseException, FailedRequestException, EmptyDocumentFieldException, IOException, JSONException {

        DoctorParser parser = new DoctorParser();
        int arg = Integer.parseInt(args[0]);
        parser.start(arg);
    }
}
