package sk.ness.academy.domain;

import javax.persistence.*;
import java.util.Date;
import sk.ness.academy.dao.ArticleDAO;


@Entity
@Table (name = "comments")
@SequenceGenerator(name = "comments_seq_store", sequenceName = "comment_seq", allocationSize = 1)

public class Comment {
    public Comment() {
        this.createTimestamp = new Date();
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articles_seq_store")
    private Integer id;

    @Column(name = "author", length = 250)
    private String author;

    @Column(name = "text", length = 2000)
    private String text;

    @Column(name = "create_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Column(name = "article_id")
    private Integer articleID;
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedDate() {
        return createTimestamp;
    }

    public void setCreatedDate(Date createdDate) {
        this.createTimestamp = createTimestamp;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }
}