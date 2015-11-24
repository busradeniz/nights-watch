package com.nightswatch.dal.entity.user;

import com.nightswatch.dal.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_TOKEN")
@Access(AccessType.FIELD)
public class UserToken extends AbstractEntity {

    @Column(name = "TOKEN")
    private String token;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
