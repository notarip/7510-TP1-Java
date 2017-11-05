package ar.uba.fi.tdd.rulogic.model;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RuleFactory {

	public static Rule createRule(String definition) throws RuleFactoryException {
		String name = "";
		List<String> params = null;
		List<Fact> facts = null;

		name = extractName(definition);
		params = extractParams(definition);
		facts = extractFacts(definition);

		if (name.isEmpty() || params.isEmpty() || facts.isEmpty())
			throw new RuleFactoryException("The definiton is wrong");

		Rule rule = new Rule(name, params, facts);

		return rule;
	}

	private static List<Fact> extractFacts(String definition) throws RuleFactoryException {


		//padre(X,Y):-varon(X),hijo(Y,X)
		Pattern implacationsPattern = Pattern.compile(".*:-([\\(\\)a-zA-Z,].+)");
		Matcher implacationsMatcher = implacationsPattern.matcher(definition);
		String implications = "";
		if (implacationsMatcher.find())
			implications = implacationsMatcher.group(1);

		if (!validateImplicationFormat(implications))
			throw new RuleFactoryException("At least one of the facs is wrong");
			
		
		List<Fact> facts;

		try {
			facts = FactFactory.createFactsFrom(implications);
		} catch (FactFactoryException e) {
			throw new RuleFactoryException("At least one of the facs is wrong");
		}
		
		return facts;
	}
	
	

	private static Boolean validateImplicationFormat(String implications) {
	
		Pattern factsPattern = Pattern.compile("([a-z]+\\([a-zA-Z,]+\\))(,\\s*[a-z]+\\([a-zA-Z,]+\\))+");
		Matcher factsMatcher = factsPattern.matcher(implications);

		return factsMatcher.matches();

	}

	private static List<String> extractParams(String definition) {

		Pattern p = Pattern.compile(".*\\(([a-zA-Z,]+)\\):-.*");
		Matcher m = p.matcher(definition);
		String param = "";
		if (m.find())
			param = m.group(1);

		List<String> params = Arrays.asList(param.split(","));
		params = params.stream().filter(elem -> !"".equals(elem)).collect(Collectors.toList());

		return params;
	}

	private static String extractName(String definition) {

		Pattern p = Pattern.compile("(.+)\\(.*:-");
		Matcher m = p.matcher(definition);
		String name = "";
		if (m.find())
			name = m.group(1);

		return name;
	}

	public static List<Rule> createRulesFrom(List<String> ruleList) throws RuleFactoryException {

		List<Rule> rules = ruleList.stream()
				.map(elem -> {
				try {
					return createRule(elem);
				} catch (RuleFactoryException e) {
					return null;
				}
			}).collect(Collectors.toList());
		
		boolean error = rules.stream().anyMatch(elem -> elem == null);
		
		if(error)
			throw new RuleFactoryException("At least one rule is wrong");
		
		return rules;
	}

}
