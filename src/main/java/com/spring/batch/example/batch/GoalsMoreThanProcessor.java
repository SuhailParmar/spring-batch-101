package com.spring.batch.example.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.spring.batch.example.model.GoalsForATeam;
import com.spring.batch.example.model.Match;


public class GoalsMoreThanProcessor implements ItemProcessor<GoalsForATeam, GoalsForATeam> {

    @Value("${score.threshold}")
    private Integer scoreThreshold;

    public GoalsMoreThanProcessor() {}

    /**
     * As we do not know if the team is a Home or away team we need to process each match
     * @param match - One match, contains goal information
     * @return Model which only contains the team of interest and the goals they have scored
     */
    @Override
    public GoalsForATeam process(GoalsForATeam match){
        System.out.printf("Processing if [%s] scored more than [%s] goals in %s\n", match.getTeam(), scoreThreshold, match);
        return (match.getGoalsScored() > scoreThreshold) ? match : null;
    }
}
