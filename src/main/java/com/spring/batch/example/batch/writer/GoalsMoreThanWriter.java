package com.spring.batch.example.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spring.batch.example.model.GoalsForATeam;

@Component
public class GoalsMoreThanWriter implements ItemWriter<GoalsForATeam> {

    @Value("${score.threshold}")
    private Integer scoreThreshold;

    @Value("${team}")
    private String team;

    @Override
    public void write(List<? extends GoalsForATeam> goalsForATeam){
        System.out.printf("Writing to console that [%s] scored more than [%s] goals [%s] times during this chunked period.\n", team, scoreThreshold, goalsForATeam.size());
        if(!goalsForATeam.isEmpty()){
            goalsForATeam.forEach(this::writeToConsole);
        }
    }

    private void writeToConsole(GoalsForATeam goalForATeam){
        System.out.printf("Writing in Game [%s] [%s] scored [%s]\n", goalForATeam.getId(), goalForATeam.getTeam(), goalForATeam.getGoalsScored());
    }
}
