package uk.co.bbc.team12.analyser.comments;

import com.google.cloud.language.samples.Analyze;
import com.google.cloud.language.v1.PartOfSpeech;
import com.google.cloud.language.v1.Token;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import org.apache.commons.csv.CSVFormat;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommentsFile implements Analysable {

    private final String filename;
    private static Words results;

    public CommentsFile(String filename) {
        this.filename = filename;
    }

    public Words getResults() throws URISyntaxException, IOException {
        if(results == null) {
            results = new Words();
            URL url = Resources.getResource(filename);
            File file = new File(url.toURI());

            try (Stream<String> stream = Files.lines(file.toPath())) {
                List<String> comments = getCommentsAboveScore(stream, 5);
                
                runTfldf(comments);
                
                String commentsString = comments.stream().reduce((comment1, comment2) -> {
                    		return comment1 += " " + comment2;
                }).get();

                String[] parts =  {commentsString.substring(0, commentsString.length()/2),commentsString.substring((commentsString.length()/2)+1)};
                for (String part : parts) {
                    results = ResultsGenerator.populateResults(part, results);
                }
            }
        }
        return results;
    }

	private void runTfldf(List<String> comments) {
		List<Comment> commentObjects = comments.stream().map(Comment::new).collect(Collectors.toList());
		
		commentObjects.stream().parallel().forEach(comment ->{
			for(String term: comment.getTerms()) {
                double v = comment.tfIdf(commentObjects, term);
                if(v < 0.5) {
                    results.addToBlackList(term);
				}
			}
		});
	}

	private List<String> getCommentsAboveScore(Stream<String> stream, int score) {
		List<String> comments = stream
		        .map(line -> {
		                try {
		                    return CSVFormat.EXCEL.withHeader("date", "comment", "respid", "progtitle", "gender", "age", "ai_score").parse(new StringReader(line)).getRecords().get(0);
		                } catch (IOException e) {
		                    throw new RuntimeException(e);
		                }
		            })
		        .filter(csvRecord -> Integer.parseInt(csvRecord.get("ai_score")) > score)
		        .map(csvRecord -> csvRecord.get("comment"))
		        .collect(Collectors.toList());
		return comments;
	}
}
