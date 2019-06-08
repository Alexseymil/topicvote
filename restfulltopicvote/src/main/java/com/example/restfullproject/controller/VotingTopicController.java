package com.example.restfullproject.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restfullproject.component.VotingTopicResourceAssembler;
import com.example.restfullproject.exception.VotingTopicNotFoundException;
import com.example.restfullproject.model.Status;
import com.example.restfullproject.model.VotingTopic;
import com.example.restfullproject.repository.VotingTopicRepository;

@RestController
public class VotingTopicController {

	private final VotingTopicRepository votingRepository;
	private final VotingTopicResourceAssembler assembler;

	public VotingTopicController(VotingTopicRepository votingRepository, VotingTopicResourceAssembler assembler) {

		this.votingRepository = votingRepository;
		this.assembler = assembler;
	}

	@GetMapping("/votes")
	public Resources<Resource<VotingTopic>> all() {

		List<Resource<VotingTopic>> topics = votingRepository.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(topics, linkTo(methodOn(VotingTopicController.class).all()).withSelfRel());
	}
	
	@GetMapping("/votes/allfinish")
	public Resources<Resource<VotingTopic>> allFinish() {

		List<Resource<VotingTopic>> topics = votingRepository.findAll().stream().filter(t -> t.getStatus() == Status.COMPLETED).map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(topics, linkTo(methodOn(VotingTopicController.class).all()).withSelfRel());
	}

	@GetMapping("/votes/{id}")
	public Resource<VotingTopic> one(@PathVariable Long id) {
		return assembler
				.toResource(votingRepository.findById(id).orElseThrow(() -> new VotingTopicNotFoundException(id)));
	}
	
	@PostMapping("/votes")
	ResponseEntity<Resource<VotingTopic>> newTopic(@RequestBody VotingTopic topic){
		
		topic.setStatus(Status.CREATED);
		VotingTopic newTopic = votingRepository.save(topic);
		
		return ResponseEntity
			      .created(linkTo(methodOn(VotingTopicController.class).one(newTopic.getId())).toUri())
			      .body(assembler.toResource(newTopic));
	}
	
	@DeleteMapping("/votes/{id}/finish")
	public ResponseEntity<ResourceSupport> finish(@PathVariable Long id){
		
		VotingTopic topic = votingRepository.findById(id).orElseThrow(() -> new VotingTopicNotFoundException(id));
		
		if(topic.getStatus() == Status.IN_PROGRESS) {
			topic.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toResource(votingRepository.save(topic)));
		}
		
		return ResponseEntity
			    .status(HttpStatus.METHOD_NOT_ALLOWED).body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + topic.getStatus() + " status"));
	}
	
	@PutMapping("/votes/{id}/start")
	public ResponseEntity<ResourceSupport> start(@PathVariable Long id){
		
		VotingTopic topic = votingRepository.findById(id).orElseThrow(() -> new VotingTopicNotFoundException(id));
		
		if(topic.getStatus() == Status.CREATED) {
			topic.setStatus(Status.IN_PROGRESS);
			return ResponseEntity.ok(assembler.toResource(votingRepository.save(topic)));
		}
		
		return ResponseEntity
			    .status(HttpStatus.METHOD_NOT_ALLOWED).body(new VndErrors.VndError("Method not allowed", "You can't start an topic that is in the " + topic.getStatus() + " status"));
	}
	
	@PutMapping("/votes/{id}/yes")
	public ResponseEntity<ResourceSupport> yes(@PathVariable Long id){
		
		VotingTopic topic = votingRepository.findById(id).orElseThrow(() -> new VotingTopicNotFoundException(id));
		
		if(topic.getStatus() == Status.IN_PROGRESS) {
			topic.setYes(topic.getYes() + 1);
			return ResponseEntity.ok(assembler.toResource(votingRepository.save(topic)));
		}
		
		return ResponseEntity
			    .status(HttpStatus.METHOD_NOT_ALLOWED).body(new VndErrors.VndError("Method not allowed", "You can't set yes an topic that is in the " + topic.getStatus() + " status"));
	}
	
	@PutMapping("/votes/{id}/no")
	public ResponseEntity<ResourceSupport> no(@PathVariable Long id){
		
		VotingTopic topic = votingRepository.findById(id).orElseThrow(() -> new VotingTopicNotFoundException(id));
		
		if(topic.getStatus() == Status.IN_PROGRESS) {
			topic.setNo(topic.getNo() + 1);
			return ResponseEntity.ok(assembler.toResource(votingRepository.save(topic)));
		}
		
		return ResponseEntity
			    .status(HttpStatus.METHOD_NOT_ALLOWED).body(new VndErrors.VndError("Method not allowed", "You can't set no an topic that is in the " + topic.getStatus() + " status"));
	}

}
