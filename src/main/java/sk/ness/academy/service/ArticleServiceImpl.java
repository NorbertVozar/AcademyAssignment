package sk.ness.academy.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Resource
  private CommentDAO commentDAO;
  @Override
  public Article findByID(final Integer articleId) {
    Article article = this.articleDAO.findByID(articleId);
    if (article == null) {
      return null;
    } else {
      article.setComments(this.commentDAO.findByArt(articleId));
      return article;
    }
  }

  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      List<Article> articles = mapper.readValue(jsonArticles, new TypeReference<>() {});

      articles.forEach(art -> articleDAO.persist(art));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Override
  public void deleteArticleByID(Integer articleID) {
    this.articleDAO.deleteByID(articleID);
  }

  @Override
  public List<Article> searchArticles(String searchByText) {
    List<Article> list = this.articleDAO.searchArticles(searchByText);
    List<Article> articles = new ArrayList<>();
    for (Article article : list) {
      article.setComments(this.commentDAO.findByArt(article.getId()));
      articles.add(article);
    }
    return articles;
  }
  //public void deleteArticles()
}
