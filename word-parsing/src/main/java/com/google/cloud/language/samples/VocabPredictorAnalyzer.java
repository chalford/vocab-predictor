package com.google.cloud.language.samples;

import com.google.cloud.language.v1.PartOfSpeech;
import com.google.cloud.language.v1.Token;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import org.apache.commons.csv.CSVFormat;
import uk.co.bbc.team12.analyser.comments.Result;
import uk.co.bbc.team12.analyser.comments.Results;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VocabPredictorAnalyzer {

    private static final Results results = new Results();
    private static Set<String> allowedTypes = Sets.newHashSet("ADJ", "VERB", "X", "NOUN");

    public static void main(String[] args) {
        try {
            processFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processFile() throws Exception {
        URL url = Resources.getResource("eastenders_verbatim_2017.csv");
        File file = new File(url.toURI());

        try (Stream<String> stream = Files.lines(file.toPath())) {
            String comments = stream.map(line -> {
//                System.out.println(line);
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
                try {
                    List<Token> tokens = Analyze.analyzeSyntaxText(part);
                    for (Token token : tokens) {
                        String tag = token.getPartOfSpeech().getTag().getValueDescriptor().getName();
                        if (allowedTypes.contains(tag) && !token.getPartOfSpeech().getProper().equals(PartOfSpeech.Proper.PROPER)) {
                            results.addWord(token.getText().getContent());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        List<Result> collect = results.getAllResults().stream().sorted(Comparator.comparingInt(Result::getOccurrences)).collect(Collectors.toList());


    }

}
