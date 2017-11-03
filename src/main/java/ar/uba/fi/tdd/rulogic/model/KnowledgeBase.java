package ar.uba.fi.tdd.rulogic.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeBase {

	private String database;
	private List<String> definitions;
	private List<String> facts;
	
	public boolean answer(String query) {
		return true;
	}
	
	public KnowledgeBase setDatabase(String database) {
		this.database = database;
		this.setDefinitions(parseDefinitions(database));
		return this;
	}

	private List<String> parseDefinitions(String database) {
		String[] splited = database.split("\\s*\\.\n\\s*");
		List<String> definitions = Arrays.asList(splited);
		List<String> cleanDefinitions = definitions.stream().map(s -> s.replaceAll("\\.", "")).collect(Collectors.toList());
		return cleanDefinitions;
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

	public List<String> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<String> definitions) {
		this.definitions = definitions;
	}

}
