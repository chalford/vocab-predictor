package uk.co.bbc.team12.analyser.comments;

import java.util.List;

public class Comment {

	private String comment;

	public Comment(String comment) {
		this.comment = comment;
	}
	
	public double termFrequency(String term) {
		double result = 0;
		String[] commentTerms = getTerms();
        for (String word : commentTerms) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / commentTerms.length;
	}
	
	public String toString() {
		return comment;
	}
	
	public double inverseDocumentFrequency(List<Comment> comments, String term) {
        double n = 0;
        for (Comment eachComment : comments) {
            if (eachComment.termFrequency(term) > 0) {
                n++;
            }
        }
        return Math.log(comments.size() / n);
	}

	public double tfIdf(List<Comment> comments, String term) {
		return termFrequency(term) * inverseDocumentFrequency(comments, term);
	}

	public String[] getTerms() {
		// TODO Auto-generated method stub
		return comment.split("\\W");
	}
}
