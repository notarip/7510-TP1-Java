package ar.uba.fi.tdd.rulogic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule extends Definition{
	
	private String name;
	private List<String> params;
	private List<Fact> facts;

	public Rule(String name, List<String> params, List<Fact> facts) {
		this.setName(name);
		this.setParams(params);
		this.setFacts(facts);

	}

	public String getName() {
		return name;
	}

	public Rule setName(String name) {
		this.name = name;
		return this;
	}

	public List<Fact> getFacts() {
		return facts;
	}

	public Rule setFacts(List<Fact> facts) {
		this.facts = facts;
		return this;
	}

	public List<String> getParams() {
		return params;
	}

	public Rule setParams(List<String> params) {
		this.params = params;
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		Rule r = (Rule)obj;

		return r.getFacts().equals(facts) &&
		r.getName().equals(name) &&
		r.getParams().equals(params);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	private Map<String, String> buildParametersMap(List<String> params) {
		Map<String, String> map = new HashMap<String,String>();
		int i = 0; 
		for (String genericParam : this.params) {
			map.put(genericParam, params.get(i));
			i++;
		}
		return map;
	}

	public List<Fact> buildFactsToEvaluate(List<String> params) {

		Map<String, String> map = buildParametersMap(params);
		List<Fact> factsToEval = new ArrayList<Fact>();
		for (Fact fact : facts) {
			List<String> realParams = new ArrayList<String>();
			for (String param : fact.getParams()) {
				realParams.add(map.get(param));
			}
			factsToEval.add(FactFactory.createFact(fact.getName(),realParams));
		}
		return factsToEval;
	}

}
