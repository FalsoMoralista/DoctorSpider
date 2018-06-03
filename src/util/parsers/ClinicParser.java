/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.parsers;

import exceptions.EmptyDocumentFieldException;
import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import model.Address;
import model.Clinic;
import model.Insurance;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import spiders.DoctorCrawler;

/**
 *
 * @author Luciano Araujo Dourado Filho
 */
public class ClinicParser {

    String[] links = {"https://www.doctoralia.com.br/centro-medico/climed-+medicos+associados-2510473",
        "https://www.doctoralia.com.br/centro-medico/clinica+do+calculo+renal-2529081",
        "https://www.doctoralia.com.br/centro-medico/self+clinica+psicanalitica-2534858",
        "https://www.doctoralia.com.br/centro-medico/clinica+reability+sao+caetano+do+sul-2485761"};

    private DoctorCrawler crawler = new DoctorCrawler();

    public ClinicParser() {
    }

    public LinkedList<Clinic> getClinics() throws InvalidTypeOfResponseException, FailedRequestException, EmptyDocumentFieldException {
        LinkedList<Clinic> clinics = new LinkedList<>();
        Document doc = null;
        for (String s : links) {
            crawler.crawl(s);
            doc = crawler.getDocument();
            clinics.add(this.parse(doc));
        }
        return clinics;
    }

    private Clinic parse(Document doc) {
        Clinic clinic = null;
        Elements elements = doc.getElementsByClass("title");
        String name = elements.select("h1").text();
        elements = doc.getElementsByClass("insurances");
        LinkedList<String> parsedInsurances = new LinkedList<>();
        StringTokenizer token = new StringTokenizer(elements.text(),",");
        while(token.hasMoreTokens()){
            parsedInsurances.add(token.nextToken());
        }        
        LinkedList<Insurance> insurances = new LinkedList<>();
        parsedInsurances.forEach(p -> insurances.add(new Insurance(p)));
        elements = doc.getElementsByClass("address");
        String addr = elements.text().substring(0,elements.text().indexOf("|")-1);
        String lat = elements.select("a").attr("data-lat");
        String lng = elements.select("a").attr("data-lng");
        clinic = new Clinic(name,new Address(lat,lng,addr),insurances);
        return clinic;
    }
    public static void main(String[]args) throws InvalidTypeOfResponseException, FailedRequestException, EmptyDocumentFieldException{
        ClinicParser parser = new ClinicParser();
        parser.getClinics();
    }
}
