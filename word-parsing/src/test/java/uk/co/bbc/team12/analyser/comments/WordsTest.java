package uk.co.bbc.team12.analyser.comments;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class WordsTest {

    @Test
    public void test_deserialisation() throws IOException {
        URL url = Resources.getResource("results.json");
        String json = Resources.toString(url, Charsets.UTF_8);
        Words words = Words.fromString(json);
        System.out.println(words);
    }

}