package uk.co.bbc.team12.analyser.comments;

import com.google.cloud.language.samples.Analyze;
import com.google.cloud.language.v1.PartOfSpeech;
import com.google.cloud.language.v1.Token;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class ResultsGenerator {

    private static Set<String> allowedTypes = Sets.newHashSet("ADJ", "VERB", "X", "NOUN");

    public static Words populateResults(String source, Words results) {
        try {
            List<Token> tokens = Analyze.analyzeSyntaxText(source);
            for (Token token : tokens) {
                String tag = token.getPartOfSpeech().getTag().getValueDescriptor().getName();
                if (allowedTypes.contains(tag) && !token.getPartOfSpeech().getProper().equals(PartOfSpeech.Proper.PROPER)) {
                    results.addWord(token.getText().getContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

}
