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
public class Street implements Serializable{
    private String name;
    private Neighbourhood neighbourhood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public Street(String name, Neighbourhood neighbourhood) {
        this.name = name;
        this.neighbourhood = neighbourhood;
    }
}
