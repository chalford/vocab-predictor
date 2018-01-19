package uk.co.bbc.team12.analyser.comments;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ArticleFile implements Analysable {

    private final String articleFilePath;

    public ArticleFile(String articleFilePath) {
        this.articleFilePath = articleFilePath;
    }

    public static void main(String[] args) {
        ArticleFile file = new ArticleFile("article.json");
        try {
            file.getResults();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Words getResults() throws URISyntaxException, IOException {
        Gson gson = new Gson();
        URL url = Resources.getResource(articleFilePath);

        String json = Resources.toString(url, Charsets.UTF_8);
//        System.out.println(json);
        Article article = gson.fromJson(json, Article.class);
//        System.out.println(article.getBody());
        Words results = ResultsGenerator.populateResults(article.getBody(), new Words());
//        System.out.println(results.getAllResults());
        return results;
    }
}
