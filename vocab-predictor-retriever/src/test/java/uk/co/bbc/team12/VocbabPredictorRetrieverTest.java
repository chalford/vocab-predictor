package uk.co.bbc.team12;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VocbabPredictorRetrieverTest {

	private VocbabPredictorRetriever retriever = new VocbabPredictorRetriever();
	
	@Test
	public void testHandleRequest() {
		APIGatewayResponse response = retriever.handleRequest(null, null);
		assertEquals("{\"terms\":[{\"value\":\"green\",\"frequency\":5035},{\"value\":\"purple\",\"frequency\":2546},{\"value\":\"blue\",\"frequency\":1754},{\"value\":\"pink\",\"frequency\":578},{\"value\":\"turquoise\",\"frequency\":83}]}", response.getBody());
	}

}
