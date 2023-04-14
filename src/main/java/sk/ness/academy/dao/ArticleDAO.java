package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);
	void ingestArticles(String jsonArticles);
	void deleteByID(Integer articleID);

	List<Article> searchArticles(String searchByText);
}
