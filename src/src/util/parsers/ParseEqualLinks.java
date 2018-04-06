/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.parsers;

import exceptions.FailedRequestException;
import exceptions.InvalidTypeOfResponseException;
import interfaces.IParser;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import spiders.DoctorCrawler;

/**
 *
 * @author luciano
 */
public class ParseEqualLinks implements IParser {

    private String source;

    public ParseEqualLinks(String source) {
        this.source = source;
    }

    @Override
    public List<String> parse() {
        List<String> list = new LinkedList();
        try {
            DoctorCrawler crawler = new DoctorCrawler();
            int counter = 1;
            while (crawler.crawl(source+"/"+counter)) {
                list.add(source+"/"+counter++);
            }
        } catch (InvalidTypeOfResponseException ex) {
            Logger.getLogger(ParseEqualLinks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailedRequestException ex) {
            return list;
        }
        return list;
    }
}
