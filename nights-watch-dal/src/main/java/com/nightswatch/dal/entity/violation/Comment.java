package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.user.User;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "COMMENT")
@Access(AccessType.FIELD)
public class Comment extends AbstractEntity {

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    // TODO Blob CLOB Tarzi bir veri yapisi
    @Column(name = "CONTENT", length = 500)
    private String content;

    @Column(name = "COMMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;

    @ManyToMany(targetEntity = Media.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "COMMENT_MEDIAS",
            joinColumns = {@JoinColumn(name = "COMMENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MEDIA_ID")})
    private Collection<Media> medias;

    @ManyToOne(targetEntity = Violation.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VIOLATION_ID")
    private Violation violation;

    public Comment() {
        this.commentDate = new Date();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Collection<Media> getMedias() {
        return medias;
    }

    public void setMedias(Collection<Media> medias) {
        this.medias = medias;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }
}
