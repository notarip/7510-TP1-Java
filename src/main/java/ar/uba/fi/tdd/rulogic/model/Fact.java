package ar.uba.fi.tdd.rulogic.model;

import java.util.List;

public class Fact extends Definition{

	private String name;
	private List<String> params;

	public Fact(String name, List<String> params) {
		this.name = name;
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

}
