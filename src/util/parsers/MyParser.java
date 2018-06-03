/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import interfaces.IParser;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Address;
import model.Clinic;
import model.Doctor;
import model.Insurance;
import model.Specialty;
import model.Subspecialty;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author luciano
 */
public class MyParser implements IParser {

    private static Document doc;

    public MyParser(Document doc) {
        this.doc = doc;
    }

    public static void setDocument(Document doc) {
        MyParser.doc = doc;
    }

    @Override
    public List<String> parse() {
        List<String> attrs = new LinkedList();
        Elements elements = doc.getElementsByClass("title").select("h1");
        elements.forEach(e -> attrs.add(e.text() + " "));

        elements = doc.getElementsByClass("title").select("p");
        attrs.add(elements.first().text() + " ");

        elements = doc.getElementsByClass("regnum");
        String s = elements.select("p").last().text();
        attrs.add(s.substring(s.indexOf(":") + 2, s.length()) + " ");

        elements = doc.getElementsByClass("subspecialities");
        attrs.add(elements.text() + " ");

        elements = doc.getElementsByClass("insurances");
        for (Element e : elements) {
            attrs.add(e.text() + " ");
        }

        elements = doc.getElementsByClass("address");
        for (Element e : elements) {
            attrs.add(e.text());
            attrs.add("lat= " + e.select("a").attr("data-lat") + " ");
            attrs.add("long= " + e.select("a").attr("data-lng") + " ");
            attrs.add("city code= " + e.select("a").attr("data-city-key") + " ");
        }
        return attrs;
    }

    public Doctor getDoctor() throws JSONException {

        List<String> attrs = new LinkedList();

        /*########################## NAME ##############################*/
        Elements elements = doc.getElementsByClass("title").select("h1");

        elements.forEach(e -> attrs.add(e.text() + " "));

        /*####################### ESPECIALTY ###########################*/
        elements = doc.getElementsByClass("title").select("p");

        attrs.add(elements.first().text() + " ");

        /*################# PROFESSIONAL REGISTER #######################*/
        elements = doc.getElementsByClass("regnum");

        try {
            String s = elements.select("p").last().text();
            attrs.add(s.substring(s.indexOf(":") + 2, s.length()) + " ");
        } catch (NullPointerException ex) {
            attrs.add(null);
        }

        /*#################### SUBSPECIALTIES #########################*/
        elements = doc.getElementsByClass("subspecialities");

        LinkedList<Subspecialty> subs = new LinkedList<>();

        String subspecialties = elements.text();

        subspecialties = subspecialties.replaceAll("Competência em: ", "");

        subspecialties = subspecialties.replaceAll("... ver mais", "");

        StringTokenizer token = new StringTokenizer(subspecialties, ",");

        while (token.hasMoreTokens()) {
            subs.add(new Subspecialty(token.nextToken()));
        }

        Specialty spec = new Specialty(attrs.get(1), subs);

        /*####################### INSURANCES ###########################*/
        LinkedList<LinkedList<Insurance>> list = new LinkedList<>();

        elements = doc.getElementsByClass("insurances");

        for (Element element : elements) {

            LinkedList<Insurance> insurances = new LinkedList<>();

            String insurance = elements.text();

            insurance = insurance.replaceAll("Atende: ", "");
            insurance = insurance.replaceAll("[0-9]", "");
            insurance = insurance.replaceAll("e  mais", "");

            token = new StringTokenizer(insurance, ",");
            while (token.hasMoreTokens()) {
                Insurance i = new Insurance(token.nextToken());
                if (!insurances.contains(i)) {
                    insurances.add(i);
                }
            }
            list.add(insurances);
        }

        /*####################### ADDRESSES ############################*/
        elements = doc.getElementsByClass("address");

        LinkedList<Clinic> clinics = new LinkedList<Clinic>() {
            @Override
            public String toString() {
                return super.toString().substring(1, super.toString().length() - 2);
            }
        };

        Clinic c = null;

        Address address = null;

        Iterator<Element> it = doc.getElementsByTag("script").iterator();
        LinkedList<JSONObject> valid = new LinkedList<>();
        while (it.hasNext()) {
            Element e = it.next();
            try {
                JSONObject obj = new JSONObject(e.data());
                if (!valid.contains(obj)) {
                    valid.add(obj);
                }
            } catch (JSONException ex) {
            }
        }

        int count = 0;
        int get = 0;

        for (Element e : elements) {

            String addr = e.text();

            addr = addr.replace("| Veja o consultório no mapa", "");
            addr = addr.replace("| ver mapa", "");

            String lat = e.select("a").attr("data-lat");
            String lng = e.select("a").attr("data-lng");

            address = new Address(lat, lng, addr);

            String clinicName = "Consultório";

            try {

                clinicName = (String) valid.get(count++).get("name");

                if (clinicName.trim().equals(attrs.get(0).trim())) {
                    clinicName = (String) valid.get(count++).get("name");
                }
            } catch (IndexOutOfBoundsException ex) {
            }

            try {

                LinkedList<Insurance> ins = list.get(get++);

                c = new Clinic(clinicName, address, ins);
            } catch (IndexOutOfBoundsException ex) {
                c = new Clinic(clinicName, address, null);
            }

            if (!clinics.contains(c)) {
                clinics.add(c);
            }
        }

        return new Doctor(attrs.get(0), attrs.get(2), spec, clinics);
    }
}
