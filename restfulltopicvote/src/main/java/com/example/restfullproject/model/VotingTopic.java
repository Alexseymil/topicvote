package com.example.restfullproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class VotingTopic {
	
	private @Id @GeneratedValue Long id;
	private String description;
	private int yes;
	private int no;
	private Status status;
	
	VotingTopic(){}

	public VotingTopic(String description) {
		this.description = description;
		this.yes = 0;
		this.no= 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getYes() {
		return yes;
	}

	public void setYes(int yes) {
		this.yes = yes;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + no;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + yes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VotingTopic other = (VotingTopic) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (no != other.no)
			return false;
		if (status != other.status)
			return false;
		if (yes != other.yes)
			return false;
		return true;
	}
	
	

}
