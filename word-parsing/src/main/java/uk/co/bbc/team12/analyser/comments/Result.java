package uk.co.bbc.team12.analyser.comments;

import com.google.gson.Gson;

public class Result {

    private int occurrences = 0;
    private String word;

    public Result(String word) {
        this.word = word;
    }

    public void incrementNumResults() {
        occurrences = occurrences +1;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public String getWord() {
        return word;
    }

    public static Result fromString(String s) {
        Gson parser = new Gson();
        return parser.fromJson(s, Result.class);
    }

    @Override
    public String toString() {
        return "{" +
                "\"occurrences\":" + "\""+(occurrences+1) + "\""+
                ", \"word\":\"" + word + "\""+
                '}';
    }
}
