package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;
import javax.annotation.Resource;
@Repository
public class CommentHibernateDAO implements CommentDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    @Override
    public Comment findCommentByID(Integer commentId) {
        Comment comment = this.sessionFactory.getCurrentSession().get(Comment.class,commentId);
        return comment;  }
    @SuppressWarnings("unchecked")

    @Override
    public List<Comment> findAll() {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Comment.class).list();
    }

    @Override
    public void persist(final Comment comment) {
            this.sessionFactory.getCurrentSession().saveOrUpdate(comment);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> findByArt(final Integer articleId) {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments where articleId = " + articleId).addEntity(Comment.class).list();
    }
    @Override
    public void deleteCommentByID(Integer commentID) {
        this.sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Comment.class,commentID));

    }

    @Override
    public void createComment(Comment comment) {
            this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

}
