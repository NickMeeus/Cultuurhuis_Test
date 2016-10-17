package be.vdab.entities;

import java.io.Serializable;

public class Klant implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private final String voornaam;
    private final String familienaam;
    private String straat;
    private String huisnr;
    private String postcode;
    private String gemeente;
    private final String gebruikersnaam;
    private final String paswoord;
    
    public Klant(String voornaam, String familienaam, String straat, String huisnr, String postcode, String gemeente, String gebruikersnaam, String paswoord) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.straat = straat;
        this.huisnr = huisnr;
        this.postcode = postcode;
        this.gemeente = gemeente;
        this.gebruikersnaam = gebruikersnaam;
        this.paswoord = paswoord;
    }

    public Klant(long id, String voornaam, String familienaam, String straat, String huisnr, String postcode, String gemeente, String gebruikersnaam, String paswoord) {
        this.id = id;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.straat = straat;
        this.huisnr = huisnr;
        this.postcode = postcode;
        this.gemeente = gemeente;
        this.gebruikersnaam = gebruikersnaam;
        this.paswoord = paswoord;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnr() {
        return huisnr;
    }

    public void setHuisnr(String huisnr) {
        this.huisnr = huisnr;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getPaswoord() {
        return paswoord;
    }
}