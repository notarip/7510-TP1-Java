package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class KnowledgeBaseTest {

	private static final String DB = "padre(homero,bart).";

	
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
	public void myFactsLooksGreatToo() {
		List<String> facts = new ArrayList<String>();
		facts.add("padre(homero,bart)");
		Assert.assertTrue(this.knowledgeBase.getFacts().equals(facts));
	}
	

}
