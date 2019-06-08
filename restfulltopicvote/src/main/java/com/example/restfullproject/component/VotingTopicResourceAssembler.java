package com.example.restfullproject.component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.example.restfullproject.controller.VotingTopicController;
import com.example.restfullproject.model.Status;
import com.example.restfullproject.model.VotingTopic;

@Component
public class VotingTopicResourceAssembler implements ResourceAssembler<VotingTopic, Resource<VotingTopic>> {

	@Override
	public Resource<VotingTopic> toResource(VotingTopic topic) {

		Resource<VotingTopic> votingTopicResource = new Resource<>(topic,
				linkTo(methodOn(VotingTopicController.class).one(topic.getId())).withSelfRel(),
				linkTo(methodOn(VotingTopicController.class).allFinish()).withRel("finish voting"),
				linkTo(methodOn(VotingTopicController.class).all()).withRel("voting"));

		if (topic.getStatus() == Status.CREATED) {
			votingTopicResource
					.add(linkTo(methodOn(VotingTopicController.class)
							.start(topic.getId())).withRel("start"));
		}
		if (topic.getStatus() == Status.IN_PROGRESS) {
			votingTopicResource
					.add(linkTo(methodOn(VotingTopicController.class)
							.finish(topic.getId())).withRel("finish"));
			votingTopicResource
			.add(linkTo(methodOn(VotingTopicController.class)
					.yes(topic.getId())).withRel("yes"));
			votingTopicResource
			.add(linkTo(methodOn(VotingTopicController.class)
					.no(topic.getId())).withRel("no"));
		}
		return votingTopicResource;
	}
}
