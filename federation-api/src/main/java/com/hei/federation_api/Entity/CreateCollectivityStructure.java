package com.hei.federation_api.Entity;

public class CreateCollectivityStructure {

    public String president;
    public String vicePresident;
    public String treasurer;
    public String secretary;

    public CreateCollectivityStructure() {}

    public CreateCollectivityStructure(String president, String vicePresident, String treasurer, String secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }
}