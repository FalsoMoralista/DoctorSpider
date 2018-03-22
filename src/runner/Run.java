/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import spider.Spider;
import spider.SpiderLeg;

/**
 * TODO: create mechanism to read a configuration file before run the experiment
 *
 * @author luciano
 */
public class Run {

    public static void main(String[] args) {
        Spider dr = new Spider();
        long time = System.currentTimeMillis();
        dr.search(url, word);
        System.out.println("Tempo total:\t\t" + String.format("%.2f", (System.currentTimeMillis() - time) / 1000.0 / 3600.0));
    }
}
