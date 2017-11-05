package ar.uba.fi.tdd.rulogic.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FactFactory {

	public static Fact createFact(String definition) throws FactFactoryException {
		String name = "";
		List<String> params = null;

		name = extractName(definition);
		params = extractParams(definition);

		if (name.isEmpty() || params.isEmpty())
			throw new FactFactoryException("The definiton isnt ok");

		Fact fact = new Fact(name, params);
		return fact;
	}

	private static List<String> extractParams(String definition) {

		Pattern p = Pattern.compile(".*\\(([a-zA-Z,]+)\\).*");
		Matcher m = p.matcher(definition);
		String param = "";
		if (m.find())
			param = m.group(1);

		List<String> params = Arrays.asList(param.split(","));
		params = params.stream().filter(elem -> !"".equals(elem)).collect(Collectors.toList());

		return params;
	}

	private static String extractName(String definition) {

		Pattern p = Pattern.compile("(.+)\\(.*");
		Matcher m = p.matcher(definition);
		String name = "";
		if (m.find())
			name = m.group(1);

		return name;
	}

	

	public static Fact createFact(String name, List<String> params){
		
		Fact fact = new Fact(name, params);
		return fact;
	}
	public static List<Fact> createFactsFrom(String implications) throws FactFactoryException{
		
		//varon(X),hijo(Y,X), just split doesn't validates format 
		Pattern factsPattern = Pattern.compile("(\\w+\\(?[a-zA-Z,]+\\)?),?");
		Matcher factsMatcher = factsPattern.matcher(implications);
		List<String> factsList = new ArrayList<String>();
		
		while (factsMatcher.find())
			factsList.add(factsMatcher.group(1));
		
		List<Fact> facts = factsList.stream()
				.map(elem -> {
					try {
						return FactFactory.createFact(elem);
					} catch (FactFactoryException e) {
						return null;
					}
				}).collect(Collectors.toList());

		boolean error = facts.stream().anyMatch(elem -> elem == null);
		
		if(error)
			throw new FactFactoryException("At least one implication is wrong");

		return facts;
		
	}


	
}
