package uk.co.bbc.team12.analyser.comments;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Runner {

    public static void main(String[] args) {
        ArticleFile articleFile = new ArticleFile("article.json");
        CommentsFile commentsFile = new CommentsFile("eastenders_verbatim_2017.csv");
        try {
//            Words articleResults = articleFile.getResults();

            Words commentsResults = commentsFile.getResults();
            System.out.println(commentsResults.toString());
//            Collection<Result> intersectedResults = commentsResults.getAllResultsExcept(articleResults);
//            List<Result> sorted = intersectedResults.stream().sorted(Comparator.comparingInt(Result::getOccurrences).reversed()).collect(toList());
//            for(int i=0;i<10;i++) {
//                System.out.println(sorted.get(i));
//            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
