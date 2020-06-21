package org.giu.model;

/**
 * Class that holds Band information
 */
public class Band {

    /**
     * Band unique ID;
     */
    private int id;

    /**
     * Band Name
     */
    private String name;

    /**
     * Band debut year
     */
    private int debutYear;
    
    
    public Band() {
    }
    
    public static Band of(String name, int debutYear) {
        var band = new Band();
        band.name = name;
        band.debutYear = debutYear;
        return band;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDebutYear() {
        return debutYear;
    }

    public void setDebutYear(int debutYear) {
        this.debutYear = debutYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}