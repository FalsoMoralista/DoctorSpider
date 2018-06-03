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
public class Clinic implements Serializable {

    private String name;
    private Address address;
    private LinkedList<Insurance> insurances;

    public Clinic(String name, Address address, LinkedList<Insurance> insurances) {
        this.name = name;
        this.address = address;
        this.insurances = insurances;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(LinkedList<Insurance> insurances) {
        this.insurances = insurances;
    }

    @Override
    public String toString() {
//        return "{\"clinic\":" + "\"name\":\"" + name + "\", \"address\":\"" + address + "\", \"insurances\":\"" + insurances + "\"}";

        return "{\"clinic\":{\"name\":\"" + name + "\", \"address\":" + address + ", \"insurances\":" + insurances + "}}";
    }

}
