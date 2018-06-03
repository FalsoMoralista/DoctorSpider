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
public class Address implements Serializable{
    
    private String lat;
    private String lng;
    private String addr;

    public Address(String lat, String lng, String addr) {
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
//        if(lat.isEmpty()){
//            lat = null;
//        }
//        if(lng.isEmpty()){ isso aqui foi comentado pois gerava null pointer na execucao do parse
//            lng = null;
//        }
        return "{\"name\":\""+addr + "\",\"lat\":" + lat + ",\"lng\":" + lng+"}";
    }   
}
