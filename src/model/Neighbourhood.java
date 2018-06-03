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
public class Neighbourhood implements Serializable{
    private String name;
    private City city;    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Neighbourhood(String name, City city) {
        this.name = name;
        this.city = city;
    }
}
