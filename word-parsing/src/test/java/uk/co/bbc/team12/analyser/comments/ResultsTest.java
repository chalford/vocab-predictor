package uk.co.bbc.team12.analyser.comments;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ResultsTest {

    @Test
    public void test_deserialisation() throws IOException {
        URL url = Resources.getResource("results.json");
        String json = Resources.toString(url, Charsets.UTF_8);
        System.out.println("--"+json);
        Results results = Results.fromString(json);
        results.getAllResults().stream().sorted(Comparator.comparingInt(Result::getOccurrences)).collect(Collectors.toList());
        System.out.println(results);
    }

}