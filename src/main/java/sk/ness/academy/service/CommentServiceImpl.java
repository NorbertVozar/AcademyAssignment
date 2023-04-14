package sk.ness.academy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.CommentNotFoundExc;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

    @Resource
    private ArticleDAO articleDAO;

    @Override
    public Comment findCommentByID(Integer commentId) {
        if(commentId == null){
            throw new CommentNotFoundExc();
        }
        return this.commentDAO.findCommentByID(commentId);
    }

    @Override
    public List<Comment> findAll() {
        return this.commentDAO.findAll();
    }

    @Override
    public void createComment(Integer articleId, Comment comment) {
        Article art = this.articleDAO.findByID(articleId);
        List<Comment> comm = art.getComments();
        comm.add(comment);
        art.setComments(comm);
        this.articleDAO.persist(art);
    }

    @Override
    public void ingestComments(String jsonComments) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Comment> comments = mapper.readValue(jsonComments, new TypeReference<>() {});
            comments.forEach(comm -> commentDAO.persist(comm));
        } catch (Exception e) {
            throw new CommentNotFoundExc();
        }
    }

    @Override
    public void deleteCommentById(Integer commentID) {
        if(commentID == null){
            throw new CommentNotFoundExc();
        }
        this.commentDAO.deleteCommentByID(commentID);
    }


}
