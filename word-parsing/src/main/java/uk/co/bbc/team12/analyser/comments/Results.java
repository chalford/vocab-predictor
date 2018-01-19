package uk.co.bbc.team12.analyser.comments;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Results {

    private transient Map<String, Result> results = new HashMap<>();
    private transient Set<String> blackList = Sets.newHashSet("storyline", "story", "storylines", "episode", "soap", "'s");

    private List<Result> words = new ArrayList<>();

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

    public List<Result> getWords() {
        return words;
    }

    public void setWords(List<Result> words) {
        this.words = words;
    }

    public Collection<Result> getAllResultsExcept(Results resultsToOmit) {
        List<String> wordsToOmit = resultsToOmit.getAllResults().stream().map(Result::getWord).collect(toList());
        return results.values().stream().filter(x -> !wordsToOmit.contains(x.getWord())).collect(toList());
    }

    public static Results fromString(String s) {
        Gson gson = new Gson();
        return gson.fromJson(s, Results.class);
    }

    @Override
    public String toString() {
        words = new ArrayList<>(results.values());
        System.out.println(words);
        return "{" +
                "\"words\":" + words +
                '}';
    }
}
