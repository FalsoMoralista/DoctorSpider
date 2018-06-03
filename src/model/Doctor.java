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
public class Doctor implements Serializable{
    
    private String name;
    private String register;
    private Specialty specialty;
    private LinkedList<Clinic> clinics;
    
    public Doctor(String name, String register, Specialty specialty, LinkedList<Clinic> clinics) {
        this.name = name;
        this.register = register;
        this.specialty = specialty;
        this.clinics = clinics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public LinkedList<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(LinkedList<Clinic> clinics) {
        this.clinics = clinics;
    }

    @Override
    public String toString() {
        return "{\"name\":" + "\"" + name +"\"," + "\"register\":" + "\""+register + "\"," +"\"specialty\":"+ specialty + ","+ "\"clinics\":" + clinics +"}";
//        return "{\"name\":" + "\"" + name +"\"," + "\"register\":" + "\""+register + "\"," +"\"specialty:\"" + "\""+ specialty + "\","+ "\"clinics:\"" + "\"" + clinics + "\""+"}";
//        return " "+ name + " " + register + " " + specialty + " " + clinics + " ";
    }
    
}
