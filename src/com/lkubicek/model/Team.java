package com.lkubicek.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

public class Team implements Comparable<Team> {
    private String name;
    private String coachName;
    private Set<Player> playerList = new TreeSet<>();

    public Team(String name, String coachName) {
        this.name = name;
        this.coachName = coachName;
    }

    public boolean addPlayer(Player player) {
        return playerList.add(player);
    }

    public boolean removePlayer(Player player) {
        return playerList.remove(player);
    }

    public Map<String, Set<Player>> getPlayerReport() {
        Map<String, Set<Player>> playerReport = new HashMap<>();
        Set<Player> shortPlayers = new TreeSet<>();
        Set<Player> mediumPlayers = new TreeSet<>();
        Set<Player> tallPlayers = new TreeSet<>();

        for (Player player : this.playerList) {
            if (player.getHeightInInches() <= 40) {
                shortPlayers.add(player);
            } else if (player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46) {
                mediumPlayers.add(player);
            } else {
                tallPlayers.add(player);
            }
        }

        playerReport.put("Short", shortPlayers);
        playerReport.put("Medium", mediumPlayers);
        playerReport.put("Tall", tallPlayers);

        return playerReport;
    }

    public String getName() {
        return this.name;
    }

    public String getCoachName() {
        return this.coachName;
    }

    public Set<Player> getPlayerList() {
        return this.playerList;
    }

    @Override
    public int compareTo(Team other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;

        Team team = (Team) o;

        return name.equals(team.name) && coachName.equals(team.coachName);

    }

    @Override
    public String toString() {
        return this.name + ", Coach: " + this.coachName;
    }

}