package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class KnowledgeBaseTest {

	private static final String DB_SUCKS = "padre(homero,bart).\npadrehomero,lisa).";
	
	private static final String DB = "padre(homero,bart).\n"
			+ "padre(homero,lisa).\n"
			+ "padre(homero,maggi).\n"
			+ "varon(bart).\n"
			+ "mujer(lisa).\n"
			+ "mujer(maggi).\n"
			+ "varon(javier).\n"
			+ "hijo(X,Y):-varon(X),padre(Y,X).\n"
			+ "hija(X,Y):-mujer(X),padre(Y,X).";
	private static final List<String> definitions = Arrays.asList("padre(homero,bart)",
			"padre(homero,lisa)",
			"padre(homero,maggi)",
			"varon(bart)",
			"mujer(lisa)",
			"mujer(maggi)",
			"varon(javier)",
			"hijo(X,Y):-varon(X),padre(Y,X)",
			"hija(X,Y):-mujer(X),padre(Y,X)");
	
	private static final List<Fact> FACTS = Arrays.asList(new Fact("padre", Arrays.asList("homero","bart")),
			new Fact("padre", Arrays.asList("homero","lisa")),
			new Fact("padre", Arrays.asList("homero","maggi")),
			new Fact("varon", Arrays.asList("bart")),
			new Fact("mujer", Arrays.asList("lisa")),
			new Fact("mujer", Arrays.asList("maggi")),
			new Fact("varon", Arrays.asList("javier"))); 
		
	private static final List<Rule> RULES = Arrays.asList(
			new Rule("hijo", Arrays.asList("X","Y"), Arrays.asList(new Fact("varon", Arrays.asList("X")),new Fact("padre", Arrays.asList("Y","X")))),
			new Rule("hija", Arrays.asList("X","Y"), Arrays.asList(new Fact("mujer", Arrays.asList("X")),new Fact("padre", Arrays.asList("Y","X"))))
			);

	
	@InjectMocks
	private KnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		knowledgeBase.setDatabase(DB);
	}

	@Test
	public void test() {
		Assert.assertTrue(this.knowledgeBase.answer("varon (javier)."));
	}
	
	@Test
	public void myDataBaseLooksGreat() {
		Assert.assertTrue(this.knowledgeBase.getDatabase().equals(DB));
	} 
	
	@Test
	public void mydefinitionsLooksGreatToo() {
		knowledgeBase.setDefinitions(definitions);
		Assert.assertTrue(this.knowledgeBase.getDefinitions().equals(definitions));
	}
	
	@Test
	public void myDataBaseSucks() {
		KnowledgeBase kb = new KnowledgeBase();
		kb.setDatabase(DB_SUCKS);
		Assert.assertFalse(kb.validateDataBaseIntegrity());
	}

	@Test
	public void validateFact() throws FactFactoryException {
		Fact fact = FactFactory.createFact("varon(juan)");
		Fact controlFact = new Fact("varon", Arrays.asList("juan"));
		Assert.assertTrue(controlFact.equals(fact));
	}
	
	@Test(expected= FactFactoryException.class)
	public void failsOnWrongFact() throws FactFactoryException {
		FactFactory.createFact("varon(juan");
	}
	
	@Test
	public void validateRule() throws RuleFactoryException, FactFactoryException {
		
		Rule rule = RuleFactory.createRule("padre(X,Y):-varon(X),hijo(Y,X)");
		Fact f1 = FactFactory.createFact("varon(X)");
		Fact f2 = FactFactory.createFact("hijo(Y,X)");
		
		Rule controlRule = new Rule("padre",Arrays.asList("X","Y") ,Arrays.asList(f1,f2));
		Assert.assertTrue(controlRule.equals(rule));
	}
	
	@Test(expected= RuleFactoryException.class)
	public void failsOnWrongRule() throws RuleFactoryException {
		RuleFactory.createRule("padre(X,Y):-varon(X,hijo(Y,X)");
	}
	
	@Test
	public void kbCointainsAllFacts() {
		Assert.assertTrue(knowledgeBase.getFacts().equals(FACTS));
	}
	
	@Test
	public void kbCointainsAllRules() {
		Assert.assertTrue(knowledgeBase.getRules().equals(RULES));
	}
	
	@Test
	public void factEvaluation() {
		Assert.assertTrue(knowledgeBase.answer("padre(homero,lisa)"));
	}
	
	@Test
	public void ruleEvaluation() {
		Assert.assertTrue(knowledgeBase.answer("hijo(bart,homero)"));
	}

}
