package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.domain.Comment;

public interface CommentService {

    /** Returns {@link Comment} with provided ID */
    Comment findCommentByID(Integer commentId);

    /** Returns all available {@link Comment}s */
    List<Comment> findAll();

    /** Creates new {@link Comment} */
    void createComment(Integer articleId, Comment comment);

    /** Creates new {@link Comment}s by ingesting all articles from json */
    void ingestComments(String jsonComments);

    /** Deletes selected {@link Comment} */

    void deleteCommentById(Integer commentID);
}
