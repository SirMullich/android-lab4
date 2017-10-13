package com.example.daulet.lab3.models;

/**
 * Created by daulet on 10/13/17.
 */

public class Role {
    private String name;
    private int numberOfHeroes;
    private int imageIndex;

    // Empty constructor
    public Role() {

    }

    public Role(String name, int numberOfHeroes, int imageIndex) {
        this.name = name;
        this.numberOfHeroes = numberOfHeroes;
        this.imageIndex = imageIndex;
    }

    // Getter and Setter for name
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for numberOfHeroes
    public int getNumberOfHeroes() {
        return this.numberOfHeroes;
    }
    public void setNumberOfHeroes(int numberOfHeroes) {
        this.numberOfHeroes = numberOfHeroes;
    }

    // Getter and Setter for imageIndex
    public int getImageIndex() {
        return this.imageIndex;
    }
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}
