package uk.co.bbc.team12;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import uk.co.bbc.team12.analyser.comments.Runner;

public class VocbabPredictorRetrieverTest {

	private VocbabPredictorRetriever retriever;
	
	@Before
	public void setup() {
		retriever = new VocbabPredictorRetriever();
	}
	
	@Test
	public void testHandleRequest() {
		Map<String, Object> eventMap = new HashMap<String, Object>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("audience", "colours");
		eventMap.put("queryStringParameters", queryParams);
		
		APIGatewayResponse response = retriever.handleRequest(eventMap, null);
		assertEquals("{\"terms\":[{\"value\":\"green\",\"frequency\":5035},{\"value\":\"purple\",\"frequency\":2546},{\"value\":\"blue\",\"frequency\":1754},{\"value\":\"pink\",\"frequency\":578},{\"value\":\"turquoise\",\"frequency\":83}]}", response.getBody());
	}
	
	@Test
	public void testHandleRequestReal() {
		InputStream eastendersIS = Runner.class.getClassLoader().getResourceAsStream("eastenders_word_frequency.json");
		S3ObjectInputStream s3IS = new S3ObjectInputStream(eastendersIS, null);
		AmazonS3 mockClient = Mockito.mock(AmazonS3.class);
		S3Object object = Mockito.mock(S3Object.class);
		when(mockClient.getObject(anyString(), anyString())).thenReturn(object);
		when(object.getObjectContent()).thenReturn(s3IS);
		Map<String, Object> eventMap = new HashMap<String, Object>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("audience", "Eastenders");
		eventMap.put("queryStringParameters", queryParams);
		
		APIGatewayResponse response = retriever.handleRequest(eventMap, null);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}

}
