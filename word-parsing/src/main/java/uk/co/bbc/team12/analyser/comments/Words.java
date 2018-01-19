package uk.co.bbc.team12.analyser.comments;

import com.google.common.collect.Sets;
import com.google.gson.Gson;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Words {

    private transient Map<String, Result> results = new HashMap<>();
    private List<Result> words;
    private transient Set<String> blackList = Sets.newHashSet("storyline", "story", "storylines", "episode", "soap", "'s");

    public List<Result> getWords() {
        return words;
    }

    public void addWord(String word) {
        word = word.toLowerCase();
        if(blackList.contains(word)) return;
        Result result = results.get(word);
        if(result != null) {
            result.incrementNumResults();
        } else {
            results.put(word, new Result(word));
        }
    }

    public Collection<Result> getAllResults() {
        return results.values();
    }

    public Collection<Result> getAllResultsExcept(Words resultsToOmit) {
        List<String> wordsToOmit = resultsToOmit.getAllResults().stream().map(Result::getWord).collect(toList());
        return results.values().stream().filter(x -> !wordsToOmit.contains(x.getWord())).collect(toList());
    }

    public static Words fromString(String s) {
        Gson gson = new Gson();
        return gson.fromJson(s, Words.class);
    }

    @Override
    public String toString() {
        if(results.size() > 0) {
            words = new ArrayList<>(results.values());
        }
        return new Gson().toJson(this);
    }
}
