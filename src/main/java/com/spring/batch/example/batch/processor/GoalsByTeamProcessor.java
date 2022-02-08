package com.spring.batch.example.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.spring.batch.example.model.GoalsForATeam;
import com.spring.batch.example.model.Match;


public class GoalsByTeamProcessor implements ItemProcessor<Match, GoalsForATeam> {

    @Value("${team}")
    private String team;

    public GoalsByTeamProcessor() {}

    /**
     * As we do not know if the team is a Home or away team we need to process each match
     * @param match - One match, contains goal informations
     * @return The team of interest and the number of goals they scored in that match
     */
    @Override
    public GoalsForATeam process(Match match){
        System.out.printf("Processing %s\n", match);
        if(match.getHomeTeam().equals(team)){
            return new GoalsForATeam(match.getId(), team, match.getHomeTeamGoals());
        }else if(match.getAwayTeam().equals(team)){
            return new GoalsForATeam(match.getId(), team, match.getAwayTeamGoals());
        }else{
            return null;
        }
    }
}
