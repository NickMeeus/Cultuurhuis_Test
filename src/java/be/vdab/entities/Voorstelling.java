package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Voorstelling implements Serializable, Comparable<Voorstelling> {

    private static final long serialVersionUID = 1L;
    private final long id;
    private final String titel;
    private final String uitvoerders;
    private final Date datum;
    private final long genreid;
    private final BigDecimal prijs;
    private final int vrijePlaatsen;

    public Voorstelling(long id, String titel, String uitvoerders, Date datum, long genreid, BigDecimal prijs, int vrijeplaatsen) {
        this.id = id;
        this.titel = titel;
        this.uitvoerders = uitvoerders;
        this.datum = datum;
        this.genreid = genreid;
        this.prijs = prijs;
        this.vrijePlaatsen = vrijeplaatsen;
    }

    public long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getUitvoerders() {
        return uitvoerders;
    }

    public Date getDatum() {
        return datum;
    }

    public long getGenreid() {
        return genreid;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public int getVrijePlaatsen() {
        return vrijePlaatsen;
    }
    
    @Override
    public int compareTo(Voorstelling voorstelling) {
        return datum.compareTo(voorstelling.datum);
    }
}
