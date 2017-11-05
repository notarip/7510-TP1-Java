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
		return factQuery(query) || ruleQuery(query);
	}

	private boolean ruleQuery(String query) {

		String cleanQuery = query.replaceAll("[\\.\\s]*", "");
		List<Rule> match = null;
		Fact fact = null;
		try {
			fact = FactFactory.createFact(cleanQuery);
			String name = fact.getName();
			match = getRules().stream().filter(elem -> elem.getName().equals(name)).collect(Collectors.toList());
		} catch (FactFactoryException e) {
			//e.printStackTrace();
		}

		if (match != null && !match.isEmpty()) {
			boolean result = true;
			Rule rule = match.get(0);
			List<Fact> factsToEvaluate = rule.buildFactsToEvaluate(fact.getParams());
			for (Fact factToEval : factsToEvaluate) {
				status = result && factQuery(factToEval.toDefinition());
			}
		} else {
			return false;
		}
		return status;

	}

	private boolean factQuery(String query) {

		String cleanQuery = query.replaceAll("[\\.\\s]*", "");
		List<Fact> match = null;
		try {
			Fact fact = FactFactory.createFact(cleanQuery);
			match = getFacts().stream().filter(elem -> elem.equals(fact)).collect(Collectors.toList());
		} catch (FactFactoryException e) {
		//	e.printStackTrace();
		}
		return (match != null && !match.isEmpty());
	}

	public KnowledgeBase setDatabase(String database) {
		this.database = database;
		this.setDefinitions(parseDefinitions(database));
		return this;
	}

	private List<String> parseDefinitions(String database) {
		String[] splited = database.split("[\\.\n]+");
		List<String> definitions = Arrays.asList(splited);
		List<String> cleanDefinitions = definitions.stream().map(s -> s.replaceAll("[\\.\\s]*", ""))
				.collect(Collectors.toList());

		setStatus(extractFacts(cleanDefinitions) && extractRules(cleanDefinitions));

		return cleanDefinitions;
	}

	private boolean extractRules(List<String> definitions) {
		List<String> ruleList = definitions.stream().filter(elem -> elem.contains(":-")).collect(Collectors.toList());
		Boolean status = Boolean.TRUE;

		try {
			setRules(RuleFactory.createRulesFrom(ruleList));
		} catch (RuleFactoryException e) {
			status = Boolean.FALSE;
		}

		return status;
	}

	private Boolean extractFacts(List<String> definitions) {

		List<String> factList = definitions.stream().filter(elem -> !elem.contains(":-")).collect(Collectors.toList());
		Boolean status = Boolean.TRUE;

		try {
			setFacts(FactFactory.createFactsFrom(String.join(",", factList)));
		} catch (FactFactoryException e) {
			status = Boolean.FALSE;
		}

		return status;
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
		return status;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

}
