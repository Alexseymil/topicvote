package com.example.restfullproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restfullproject.model.VotingTopic;

public interface VotingTopicRepository extends JpaRepository<VotingTopic, Long>{

}
