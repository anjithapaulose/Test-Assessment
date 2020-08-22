package com.practice.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Sequence collection
 * 
 * @author Anjitha
 *
 */
@Document(collection = "Sequence")
public class Sequence {

	private String id;
	private long sequenceCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSequenceCount() {
		return sequenceCount;
	}

	public void setSequenceCount(long sequenceCount) {
		this.sequenceCount = sequenceCount;
	}

}
