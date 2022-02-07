package com.spring.batch.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Match {

    @Id
    private Integer id;
    private String homeTeam;
    private String awayTeam;
    private Integer homeTeamGoals;
    private Integer awayTeamGoals;

    public Match(Integer id, String homeTeam, String awayTeam, Integer homeTeamGoals, Integer awayTeamGoals) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public Match() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(Integer homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public Integer getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(Integer awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    @Override
    public String toString() {
        return String.format("Match [%s] Between [%s] and [%s] was [%s]-[%s]", id, homeTeam, awayTeam, homeTeamGoals, awayTeamGoals);
    }
}
