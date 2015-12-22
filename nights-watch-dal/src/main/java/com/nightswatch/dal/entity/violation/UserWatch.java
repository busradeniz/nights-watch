package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.dal.entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "USER_WATCH")
@Access(AccessType.FIELD)
public class UserWatch extends AbstractEntity {

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(targetEntity = Violation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VIOLATION_ID")
    private Violation violation;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }
}
