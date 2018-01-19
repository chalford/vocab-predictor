package uk.co.bbc.team12;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VocbabPredictorRetrieverTest {

	private VocbabPredictorRetriever retriever = new VocbabPredictorRetriever();
	
	@Test
	public void testHandleRequest() {
		Map<String, Object> eventMap = new HashMap<String, Object>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("audience", "colours");
		eventMap.put("queryStringParameters", queryParams);
		
		APIGatewayResponse response = retriever.handleRequest(eventMap, null);
		assertEquals("{\"terms\":[{\"value\":\"green\",\"frequency\":5035},{\"value\":\"purple\",\"frequency\":2546},{\"value\":\"blue\",\"frequency\":1754},{\"value\":\"pink\",\"frequency\":578},{\"value\":\"turquoise\",\"frequency\":83}]}", response.getBody());
	}

}
