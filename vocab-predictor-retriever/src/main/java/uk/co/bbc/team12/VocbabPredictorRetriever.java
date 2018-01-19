package uk.co.bbc.team12;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ValueNode;

import uk.co.bbc.team12.analyser.comments.Result;
import uk.co.bbc.team12.analyser.comments.Words;


/**
 * Hello world!
 *
 */
public class VocbabPredictorRetriever implements RequestHandler<Map<String, Object>, APIGatewayResponse> {
	private ObjectMapper mapper = new ObjectMapper();
	private final Regions REGION = Regions.EU_WEST_1;
	private AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION).build();
	private LambdaLogger logger;
	
	public VocbabPredictorRetriever() {
	}
	
	public void setAmazonS3(AmazonS3 s3client) {
		this.s3Client = s3client;
	}
	
	public APIGatewayResponse handleRequest(Map<String, Object> eventMap, Context context) {
		logger = getLogger(context);
		if(logger != null) {
			logger.log("Event received: " + eventMap);
		}
		Map<String, Object> queryParams = (Map<String, Object>)eventMap.get("queryStringParameters");
		String audience = null;
		if(queryParams != null) {
			audience = (String) queryParams.get("audience");
		}
		List<Term> terms = getTerms(audience);

		ValueNode termsNode = mapper.createObjectNode().pojoNode(terms);
		JsonNode jsonObject = mapper.createObjectNode().set("terms", termsNode);
		
		APIGatewayResponse response = new APIGatewayResponse();
		response.setStatusCode(200);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(Headers.CONTENT_TYPE, "application/json");
		headers.put("Access-Control-Allow-Origin", "*");
		try {
			String jsonBody = mapper.writeValueAsString(jsonObject);
			if(logger != null) {
				logger.log("Body sending back: " + jsonBody);
			}
			response.setBody(jsonBody);
			response.setHeaders(headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	private LambdaLogger getLogger(Context context) {
		if(context != null) { 
			return context.getLogger();
		} else {
			return null;
		}
	}
	
	private List<Term> getTerms(String segment) {
		segment = (segment != null) ? segment : "";
		List<Term> terms = new ArrayList<Term>();
		switch(segment) {
			case "Eastenders":
				S3Object segementData = s3Client.getObject("aggregated-suggestions", "eastenders" + "_word_frequency.json");
				InputStream segmentIS = segementData.getObjectContent();
				try {
					String theString = IOUtils.toString(segmentIS, "UTF-8");
					Words words = Words.fromString(theString);
					List<Result> wordResult = words.getWords();
					List<Result> sorted = wordResult.stream()
							.filter(result -> result.getWord().length() > 3)
							.sorted(Comparator.comparingInt(Result::getOccurrences).reversed()).collect(Collectors.toList());
					for(Result res : sorted) {
						terms.add(new Term(res.getWord(), res.getOccurrences()));
					}
					if(logger != null) {
						logger.log(terms.toString());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case "colours":
				terms.add(new Term("green", 5035));
				terms.add(new Term("purple", 2546));
				terms.add(new Term("blue", 1754));
				terms.add(new Term("pink", 578));
				terms.add(new Term("turquoise", 83));
				break;
			default:
				terms.add(new Term("one", 1234));
				terms.add(new Term("two", 4321));
				break;
		}
		
		return terms;
	}
	
}
