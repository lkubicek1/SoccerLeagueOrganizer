package com.lkubicek.model;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private int heightInInches;
    private boolean previousExperience;

    Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.heightInInches = heightInInches;
        this.previousExperience = previousExperience;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    int getHeightInInches() {
        return heightInInches;
    }

    public boolean isPreviousExperience() {
        return previousExperience;
    }

    @Override
    public int compareTo(Player other) {
        int r = this.lastName.compareToIgnoreCase(other.getLastName());
        if (r == 0)
            r = this.firstName.compareToIgnoreCase(other.getFirstName());
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return heightInInches == player.heightInInches && previousExperience == player.previousExperience && firstName.equals(player.firstName) && lastName.equals(player.lastName);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + heightInInches;
        result = 31 * result + (previousExperience ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String spacing = "";
        int spacingAmt = 32 - (this.lastName.length() + this.firstName.length());
        for (int i = 1; i<= spacingAmt; i++){
            spacing += " ";
        }
        spacing += "(";
        return this.lastName + ", " + this.firstName + spacing + this.heightInInches + " inches - " + ((this.previousExperience) ? "experienced)" : "inexperienced)");
    }
}
