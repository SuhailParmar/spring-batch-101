package com.spring.batch.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.batch.example.model.GoalsForATeam;

public interface GoalsForATeamRepository extends JpaRepository<GoalsForATeam, Integer> {
}
