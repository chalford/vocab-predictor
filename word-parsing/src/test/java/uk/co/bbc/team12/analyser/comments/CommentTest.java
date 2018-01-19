package uk.co.bbc.team12.analyser.comments;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CommentTest {

	@Test
	public void testTermFrequency() {
		Comment comment = new Comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		double termFreq = comment.termFrequency("ipsum");
		assertEquals(0.013, termFreq, 0.001);
	}
	
	@Test
	public void testInverseDocumentFrequency() {
		Comment comment1 = new Comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		Comment comment2 = new Comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(comment1);
		comments.add(comment2);
		
		// Excepteur is not in the second comment
		double termFreq = comment1.inverseDocumentFrequency(comments, "Excepteur");
		assertEquals(0.693, termFreq, 0.001);
	}

	@Test
	public void testTermFrequencyInverseDocumentFrequency() {
		Comment comment1 = new Comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia officia deserunt mollit anim id est laborum.");
		Comment comment2 = new Comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(comment1);
		comments.add(comment2);
		
		// Excepteur is not in the second comment
		double termFreq1 = comment1.tfIdf(comments, "Excepteur");
		assertEquals(0.009, termFreq1, 0.001);
		
		double termFreq2 = comment1.tfIdf(comments, "officia");
		assertEquals(0.018, termFreq2, 0.001);
	}
	
}
