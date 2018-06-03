/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Luciano Araujo Dourado Filho
 */
public class Specialty implements Serializable{
    
    private String name;
    private LinkedList<Subspecialty> subspecialties;

    public Specialty(String name, LinkedList<Subspecialty> subspecialties) {
        this.name = name;
        this.subspecialties = subspecialties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Subspecialty> getSubspecialties() {
        return subspecialties;
    }

    public void setSubspecialties(LinkedList<Subspecialty> subspecialties) {
        this.subspecialties = subspecialties;
    }        

    @Override
    public String toString() {
        return "{\"specialty\":"+"\""+name+"\"" +","+ "\"subspecialties\":\"" + subspecialties+"\""+"}";
    }
        
}
