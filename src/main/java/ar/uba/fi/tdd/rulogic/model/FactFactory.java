package ar.uba.fi.tdd.rulogic.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactFactory implements Factory {

	@Override
	public Fact createDefinition(String definition) {
		String name = "";
		List<String> params = null;
		
		name = extractName(definition);
		params = extractParams(definition);
	
		
		Fact fact = new Fact(name, params);
		return fact;
	}

	private List<String> extractParams(String definition) {
		
		Pattern p = Pattern.compile(".*");
		Matcher m = p.matcher(definition);
		String[] splited = definition.split("\\s*\\s*");
		
		return null;
	}

	
	//varon(juan)
	private String extractName(String definition) {
		
		return null;
	}

}
