package com.hei.federation_api.Entity;


public class CreateCollectivityStructure {

    public String president;
    public String vicePresident;
    public String treasurer;
    public String secretary;

    public CreateCollectivityStructure(String president, String vicePresident, String treasurer, String secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getVicePresident() {
        return vicePresident;
    }

    public void setVicePresident(String vicePresident) {
        this.vicePresident = vicePresident;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }
}