package sk.ness.academy.dao;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import com.google.gson.Gson;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
@Repository
public class ArticleHibernateDAO implements ArticleDAO {


  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    Article article = this.sessionFactory.getCurrentSession().get(Article.class, articleId);
   Hibernate.initialize(article.getComments());
    return article;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select a.id as id, a.title as title, a.text as text, a.author as author, a.create_timestamp as create_timestamp from articles a")
            .addScalar("id", IntegerType.INSTANCE)
            .addScalar("title", StringType.INSTANCE)
            .addScalar("text", StringType.INSTANCE)
            .addScalar("author", StringType.INSTANCE)
            .addScalar("create_timestamp", DateType.INSTANCE)
            .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteByID(Integer articleID) {
    this.sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Article.class,articleID));
  }
  @Override
  public void ingestArticles(String jsonArticles) {
    Arrays.stream(new Gson().fromJson(jsonArticles, Article[].class)).forEach(art -> this.sessionFactory.getCurrentSession().saveOrUpdate(art));
  }
  @SuppressWarnings("unchecked")
  @Override
  public List<Article> searchArticles(String searchByText) {
    return this.sessionFactory.getCurrentSession().createSQLQuery(
                    "select * from articles where author LIKE :par1 OR title LIKE :par2 OR text LIKE :par3")
            .setParameter("par1", "%" + searchByText + "%")
            .setParameter("par2", "%" + searchByText + "%")
            .setParameter("par3", "%" + searchByText + "%")
            .addEntity(Article.class).list();
  }
  }
