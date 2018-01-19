package uk.co.bbc.team12.analyser.comments;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Analysable {

    Words getResults() throws URISyntaxException, IOException;

}
