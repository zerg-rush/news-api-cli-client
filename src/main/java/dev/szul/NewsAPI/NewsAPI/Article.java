package dev.szul.NewsAPI.NewsAPI;

import org.json.JSONObject;

import static dev.szul.NewsAPI.config.FeatureToggles.FEATURE_TOGGLE_AUTHOR_POSTPROCESSING;

public class Article {

    private String author;
    private final String description;
    private final String title;

    public Article(JSONObject jsonArticle) {
        this.author = jsonArticle.get("author").toString();
        this.description = jsonArticle.get("description").toString();
        this.title = jsonArticle.get("title").toString();

        if (FEATURE_TOGGLE_AUTHOR_POSTPROCESSING && this.author.equals("null")) {
            this.author = "autor nieznany";
        }
    }

    @Override
    public String toString() {
        return title + ':' +
                description + ':' +
                author;
    }

}
