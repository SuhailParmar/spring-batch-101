package com.spring.batch.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GoalsForATeam {
    private Integer id;
    private String team;
    private Integer goalsScored;

    @Override public String toString() {
        return String.format("Match [%s] [%s] scored [%s] goals", getId(), getTeam(), getGoalsScored() );
    }

    public GoalsForATeam(Integer matchId, String team, Integer goalsScored){
        this.id = matchId;
        this.team = team;
        this.goalsScored = goalsScored;
    }

    public GoalsForATeam() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id public Integer getId() {
        return id;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}