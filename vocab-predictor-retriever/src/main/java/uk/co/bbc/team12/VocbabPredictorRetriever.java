package uk.co.bbc.team12;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.MimeType;

import org.apache.log4j.Logger;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.jayway.jsonpath.JsonPath;


/**
 * Hello world!
 *
 */
public class VocbabPredictorRetriever implements RequestHandler<Map<String, Object>, APIGatewayResponse>
{
	
	static final Logger log = Logger.getLogger(VocbabPredictorRetriever.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	private final Regions REGION = Regions.EU_WEST_1;

	public VocbabPredictorRetriever() {
	}

	public APIGatewayResponse handleRequest(Map<String, Object> eventMap, Context arg1) {
		log.debug("Event received: " + eventMap);
		
		APIGatewayResponse response = new APIGatewayResponse();
		response.setStatusCode(200);
		//String channel = JsonPath.read(message, "$.channel");
		//String plantUMLText = JsonPath.read(message, "$.message");
		List<Term> terms = new ArrayList<Term>();
		
		terms.add(new Term("green", 5035));
		terms.add(new Term("purple", 2546));
		terms.add(new Term("blue", 1754));
		terms.add(new Term("pink", 578));
		terms.add(new Term("turquoise", 83));
		
		ValueNode termsNode = mapper.createObjectNode().pojoNode(terms);
		JsonNode jsonObject = mapper.createObjectNode().set("terms", termsNode);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(Headers.CONTENT_TYPE, "application/json");
		headers.put("Access-Control-Allow-Origin", "*");
		try {
			response.setBody(mapper.writeValueAsString(jsonObject));
			response.setHeaders(headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
}
