package uk.co.bbc.team12.analyser.comments;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void test_deserialise(){
        System.out.println(Result.fromString("{\"word\" : \"because\",\"occurrances\" : 12}"));
    }

}