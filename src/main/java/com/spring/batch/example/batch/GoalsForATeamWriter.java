package com.spring.batch.example.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spring.batch.example.model.GoalsForATeam;
import com.spring.batch.example.repository.GoalsForATeamRepository;

@Component
public class GoalsForATeamWriter implements ItemWriter<GoalsForATeam> {

    @Value("${team}")
    private String team;

    private final GoalsForATeamRepository goalsForATeamRepository;

    @Autowired
    public GoalsForATeamWriter(GoalsForATeamRepository goalsForATeamRepository) {
        this.goalsForATeamRepository = goalsForATeamRepository;
    }

    @Override
    public void write(List<? extends GoalsForATeam> goalsForATeam){
        System.out.printf("Writing [%s] matches to H2 for during this chunked period\n", goalsForATeam.size());
        if(!goalsForATeam.isEmpty()){
            goalsForATeamRepository.saveAll(goalsForATeam);
        }
    }
}
