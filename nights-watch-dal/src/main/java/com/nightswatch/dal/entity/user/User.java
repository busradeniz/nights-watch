package com.nightswatch.dal.entity.user;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.dal.entity.Media;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * In order to use services users must be defined and this entity holds that user informations
 */
@Entity
@Table(name = "NW_USER")
@Access(AccessType.FIELD)
public class User extends AbstractEntity {

    /**
     * Username
     */
    @Column(name = "USERNAME", unique = true, length = 20)
    private String username;

    /**
     * Password which will be hashed md5
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Email information
     */
    @Column(name = "EMAIL", unique = true, length = 50)
    private String email;

    @Column(name = "FULL_NAME", length = 50)
    private String fullName;

    @Column(name = "GENDER", length = 10)
    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne(targetEntity = Media.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "MEDIA_ID")
    private Media media;

    @Column(name = "BIO"/*, length = 10000*/)
    //@Lob
    private String bio;

    @Column(name = "USER_STATUS", length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatusType userStatusType;

    /**
     * In order to use any services its roles must be assigned to user. Otherwise its access will be denied.
     */
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Collection<Role> roles;

    public User() {
        userStatusType = UserStatusType.WAITING_CONFIRMATION;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserStatusType getUserStatusType() {
        return userStatusType;
    }

    public void setUserStatusType(UserStatusType userStatusType) {
        this.userStatusType = userStatusType;
    }

    public Collection<Role> getRoles() {
        if (roles == null) {
            this.roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}
