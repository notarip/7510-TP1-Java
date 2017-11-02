package ar.uba.fi.tdd.rulogic.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnowledgeBase {

	
	private String database;
	private List<String> facts;
	
	public boolean answer(String query) {
		return true;
	}
	
	public KnowledgeBase setDatabase(String database) {
		this.database = database;
		this.setFacts(parseFacts(database));
		return this;
	}

	private List<String> parseFacts(String database) {
		List<String> facts = Arrays.asList(database.split("."));
		return facts;
	}

	public KnowledgeBase setFacts(List<String> facts) {
		this.facts = facts;
		return this;
	}
	
	public String getDatabase() {
		return database;
	}

	public List<String> getFacts() {
		return facts;
	}

}
