package dev.szul.NewsAPI.NewsAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dev.szul.NewsAPI.config.FeatureToggles.FEATURE_TOGGLE_LIMIT_NUMBER_OF_ARTICLES;
import static dev.szul.NewsAPI.config.FeatureToggles.FEATURE_TOGGLE_LIMIT_VALUE;

public class Articles {

    private final List<Article> articles = new ArrayList<>();

    public void load(JSONObject response) {
        final var jsonArticles = response.getJSONArray("articles");

        for (int i = 0; i < jsonArticles.length(); i++) {
            JSONObject jsonArticle = jsonArticles.getJSONObject(i);
            Article article = new Article(jsonArticle);
            add(article);
        }
    }

    public void add(Article article) {
        articles.add(article);
    }

    public boolean isEmpty() {
        return articles.isEmpty();
    }

    public int count() {
        return articles.size();
    }

    @Override
    public String toString() {
        return articles.stream()
                .limit(FEATURE_TOGGLE_LIMIT_NUMBER_OF_ARTICLES ?
                        FEATURE_TOGGLE_LIMIT_VALUE
                        :
                        count())
                .map(Article::toString)
                .collect(Collectors.joining (System.lineSeparator()));
    }

}
