package com.example.restfullproject.exception;

public class VotingTopicNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VotingTopicNotFoundException(Long id) {
		super("Could not find voting topic " + id);
	}
	
}
