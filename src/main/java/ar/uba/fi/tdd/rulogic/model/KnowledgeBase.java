package ar.uba.fi.tdd.rulogic.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeBase {

	private String database;
	private List<String> definitions;
	private List<Fact> facts;
	private List<Rule> rules;
	
	private Boolean status;
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

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
		
		setStatus(extractFacts(definitions) || extractRules(definitions));
		
		return cleanDefinitions;
	}

	private boolean extractRules(List<String> definitions2) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean extractFacts(List<String> definitions2) {
		// TODO Auto-generated method stub
		return false;
	}

	public KnowledgeBase setFacts(List<Fact> facts) {
		this.facts = facts;
		return this;
	}
	
	public String getDatabase() {
		return database;
	}

	public List<Fact> getFacts() {
		return facts;
	}

	public List<String> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<String> definitions) {
		this.definitions = definitions;
	}

	public Boolean validateDataBaseIntegrity() {
		// TODO Auto-generated method stub
		return status;
	}

}
