package sk.ness.academy.dao;


import java.util.List;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

public interface CommentDAO {

    /**
     * Returns {@link Comment} with provided ID
     */
    Comment findCommentByID(Integer commentId);

    /**
     * Returns all available {@link Comment}s
     */
    List<Comment> findAll();

    /**
     * Persists {@link Comment} into the DB
     */
    void persist(Comment comment);

    void deleteCommentByID(Integer commentID);
    List<Comment> findByArt(Integer articleId);
    void createComment(Comment comment);

}