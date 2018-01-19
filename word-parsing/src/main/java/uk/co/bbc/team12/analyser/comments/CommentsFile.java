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
                String comments = stream
                        .map(line -> {
                                try {
                                    return CSVFormat.EXCEL.withHeader("date", "comment", "respid", "progtitle", "gender", "age", "ai_score").parse(new StringReader(line)).getRecords().get(0);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                        .filter(csvRecord -> Integer.parseInt(csvRecord.get("ai_score")) > 5)
                        .map(csvRecord -> csvRecord.get("comment"))
                        .reduce((comment1, comment2) -> comment1 +". "+ comment2).get();

                String[] parts =  {comments.substring(0, comments.length()/2),comments.substring((comments.length()/2)+1)};
                for (String part : parts) {
                    results = ResultsGenerator.populateResults(part, results);
                }
            }
        }
        return results;
    }
}
