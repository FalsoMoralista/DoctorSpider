/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;

/**
 *
 * @author Luciano Araujo Dourado Filho
 */
public class Subspecialty implements Serializable{
    
    private String name;    

    public Subspecialty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{\"subspecialty\":\"" + name +"\""+"}";
    }
    
}
