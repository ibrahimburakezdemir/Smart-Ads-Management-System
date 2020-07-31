package com.example.reklamci;

public class Reklam {
    private String f_ad, icerik,ksure,kat;
    private int f_id;
    private  double fx,fy;

    public Reklam(){}

    public Reklam(int f_id,String f_ad,double fx, double fy, String icerik, String ksure,String kat){
        this.f_id=f_id;
        this.f_ad=f_ad;
        this.fx=fx;
        this.fy=fy;
        this.icerik=icerik;
        this.ksure=ksure;
        this.kat=kat;
    }

    public String getF_ad() {
        return f_ad;
    }

    public void setF_ad(String f_ad) {
        this.f_ad = f_ad;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getKsure() {
        return ksure;
    }

    public void setKsure(String ksure) {
        this.ksure = ksure;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public double getFx() {
        return fx;
    }

    public void setFx(double fx) {
        this.fx = fx;
    }

    public double getFy() {
        return fy;
    }

    public void setFy(double fy) {
        this.fy = fy;
    }

    public String getKat() {
        return kat;
    }

    public void setKat(String kat) {
        this.kat = kat;
    }
}
