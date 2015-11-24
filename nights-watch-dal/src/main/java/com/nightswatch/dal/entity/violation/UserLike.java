package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.dal.entity.user.User;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "USER_LIKE")
@Access(AccessType.FIELD)
public class UserLike extends AbstractEntity {

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(targetEntity = Violation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VIOLATION_ID")
    private Violation violation;

    @Column(name = "LIKE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date likeDate;
}
