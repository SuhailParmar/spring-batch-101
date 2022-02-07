package com.spring.batch.example.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.spring.batch.example.batch.GoalsMoreThanWriter;
import com.spring.batch.example.batch.GoalsByTeamProcessor;
import com.spring.batch.example.batch.GoalsForATeamWriter;
import com.spring.batch.example.batch.GoalsMoreThanProcessor;
import com.spring.batch.example.batch.MatchesReader;
import com.spring.batch.example.model.GoalsForATeam;
import com.spring.batch.example.model.Match;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Value("${chunk.size}")
    private int chunkSize;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final GoalsForATeamWriter goalsForATeamWriter;
    private final GoalsMoreThanWriter goalsMoreThanWriter;

    // @Inject == @Autowired
    // @Autowired not required as Spring understands field injection
    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                             DataSource dataSource,
                             GoalsForATeamWriter goalsForATeamWriter, GoalsMoreThanWriter goalsMoreThanWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.goalsForATeamWriter = goalsForATeamWriter;
        this.goalsMoreThanWriter = goalsMoreThanWriter;
    }

    @Bean
    public ItemProcessor<Match, GoalsForATeam> goalsByTeamProcessor() {
        return new GoalsByTeamProcessor();
    }

    @Bean
    public ItemProcessor<GoalsForATeam, GoalsForATeam> goalsMoreThanProcessor() {
        return new GoalsMoreThanProcessor();
    }

    public FlatFileItemReader<Match> goalsForTeamReader(){
        return new MatchesReader().flatFileItemReader();
    }

    public JdbcCursorItemReader jdbcCursorItemReader() {
        JdbcCursorItemReader personJdbcCursorItemReader = new JdbcCursorItemReader<>();
        personJdbcCursorItemReader.setSql("SELECT * FROM GOALS_FORATEAM");
        personJdbcCursorItemReader.setDataSource(dataSource);
        personJdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<>(GoalsForATeam.class));
        return personJdbcCursorItemReader;
    }

    @Bean
    public Job getGoalsMoreThanThresholdScoredByTeamJob() {
        return this.jobBuilderFactory.get("Get-Goals-More-Than")
                                     // Step One
                                     .start(getGoalsByTeamFromCsvStep())
                                     // Step Two
                                     .next(getGoalsMoreThanThresholdStep())
                                     .build();
    }

    public Step getGoalsByTeamFromCsvStep() {
        return stepBuilderFactory.get("Find-Goals-By-Team-From-CSV")
                                 .<Match, GoalsForATeam>chunk(chunkSize)
                                 .reader(goalsForTeamReader())
                                 .processor(goalsByTeamProcessor())
                                 .writer(goalsForATeamWriter)
                                 .build();
    }

    public Step getGoalsMoreThanThresholdStep(){
        return stepBuilderFactory.get("Get Goals more than specified threshold")
                                 .<GoalsForATeam, GoalsForATeam>chunk(chunkSize)
                                 .reader(jdbcCursorItemReader())
                                 .processor(goalsMoreThanProcessor())
                                 .writer(goalsMoreThanWriter)
                                 .build();
    }
}

