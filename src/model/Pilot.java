package model;

public class Pilot extends Person {

    private String license;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Pilot(String n, String s, String l){
        super(n,s);
        license=l;

    }

}
