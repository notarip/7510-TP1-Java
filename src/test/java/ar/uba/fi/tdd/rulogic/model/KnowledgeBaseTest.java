package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class KnowledgeBaseTest {

	private static final String DB = "padre(homero,bart).\npadre(homero,lisa).";
	private static final List<String> definitions = Arrays.asList("padre(homero,bart)","padre(homero,lisa)");

	
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

}
