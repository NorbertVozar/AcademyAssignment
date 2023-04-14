package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.ArticleNotFoundExc;
import sk.ness.academy.exception.AuthorNotFoundExc;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
	  return this.articleService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {

    if (this.articleService.findByID(articleId) == null) {
      throw new ArticleNotFoundExc();
    }
    return this.articleService.findByID(articleId);
  }

  @RequestMapping(value = "articles/search/{searchByText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchByText) {
    if (this.articleService.searchArticles(searchByText) == null) {
      throw new ArticleNotFoundExc();
    }
    return this.articleService.searchArticles(searchByText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {

    if (article.getAuthor()== null || article.getTitle()== null || article.getText()== null) {
      throw new ArticleNotFoundExc();
    }
	  this.articleService.createArticle(article);
  }


  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticleByID(@PathVariable final Integer articleId) {
    if (this.articleService.findByID(articleId) == null) {
      throw new ArticleNotFoundExc();
    }
    this.articleService.deleteArticleByID(articleId);
  }
  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
	  return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {

    if (this.authorService.findAll().isEmpty()) {
      throw new AuthorNotFoundExc();
    }
    return this.authorService.getAuthorStats();
  }
  // ~~ Comment
  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment getComment(@PathVariable final Integer commentId) {
    return this.commentService.findCommentByID(commentId);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public void removeComment(@PathVariable final Integer commentId) {
      this.commentService.deleteCommentById(commentId);
    }

  @RequestMapping(value = "comments/{articleId}", method = RequestMethod.PUT)
  public void addComment(@PathVariable final Integer articleId, @RequestBody final Comment comment) {

    if (this.articleService.findByID(articleId) == null) {
      throw new ArticleNotFoundExc();
    }

    this.commentService.createComment(articleId, comment);
  }
  @RequestMapping(value = "comments", method = RequestMethod.GET)
  public List<Comment> getAllComm() {

    return this.commentService.findAll();
  }
}