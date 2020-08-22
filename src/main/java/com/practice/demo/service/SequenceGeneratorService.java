package com.practice.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.practice.demo.model.Sequence;

@Service
public class SequenceGeneratorService {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public long generateSequnce(String seqName) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(seqName));
		Update update = new Update().inc("sequenceCount", 1);
		FindAndModifyOptions options = new FindAndModifyOptions().upsert(true);
		Sequence sequence = mongoTemplate.findAndModify(query, update, options, Sequence.class);
		
		return  sequence != null ? sequence.getSequenceCount() : 1;
	}
}
