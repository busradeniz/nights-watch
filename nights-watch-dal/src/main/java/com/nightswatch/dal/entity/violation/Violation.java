package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.user.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "VIOLATION")
@Access(AccessType.FIELD)
public class Violation extends AbstractEntity {

    @Column(name = "TITLE", length = 150)
    private String title;

    @Column(name = "VIOLATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date violationDate;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "ADDRESS", length = 300)
    private String address;

    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "VALIDATION_STATUS", length = 20)
    @Enumerated(EnumType.STRING)
    private ViolationStatusType violationStatusType;

    @Column(name = "DANGER_LEVEL", length = 10)
    @Enumerated(EnumType.STRING)
    private DangerLevelType dangerLevelType;

    @Column(name = "FREQUENCY_LEVEL", length = 10)
    @Enumerated(EnumType.STRING)
    private FrequencyLevelType frequencyLevelType;

    @ManyToOne(targetEntity = ViolationGroup.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VIOLATION_GROUP_ID")
    private ViolationGroup violationGroup;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    @ManyToMany(targetEntity = Media.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "VIOLATION_MEDIAS",
            joinColumns = {@JoinColumn(name = "VIOLATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MEDIA_ID")})
    private Collection<Media> medias;

    @ManyToMany(targetEntity = Tag.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "VIOLATION_TAGS",
            joinColumns = {@JoinColumn(name = "VIOLATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    private Collection<Tag> tags;

    @OneToMany(targetEntity = UserLike.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "violation")
    private Collection<UserLike> userLikes;

    @OneToMany(targetEntity = UserWatch.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "violation")
    private Collection<UserWatch> userWatches;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "violation")
    private Collection<Comment> comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(Date violationDate) {
        this.violationDate = violationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ViolationStatusType getViolationStatusType() {
        return violationStatusType;
    }

    public void setViolationStatusType(ViolationStatusType violationStatusType) {
        this.violationStatusType = violationStatusType;
    }

    public DangerLevelType getDangerLevelType() {
        return dangerLevelType;
    }

    public void setDangerLevelType(DangerLevelType dangerLevelType) {
        this.dangerLevelType = dangerLevelType;
    }

    public FrequencyLevelType getFrequencyLevelType() {
        return frequencyLevelType;
    }

    public void setFrequencyLevelType(FrequencyLevelType frequencyLevelType) {
        this.frequencyLevelType = frequencyLevelType;
    }

    public ViolationGroup getViolationGroup() {
        return violationGroup;
    }

    public void setViolationGroup(ViolationGroup violationGroup) {
        this.violationGroup = violationGroup;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<Media> getMedias() {
        return medias;
    }

    public void setMedias(Collection<Media> medias) {
        this.medias = medias;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public Collection<UserLike> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Collection<UserLike> userLikes) {
        this.userLikes = userLikes;
    }

    public Collection<UserWatch> getUserWatches() {
        return userWatches;
    }

    public void setUserWatches(Collection<UserWatch> userWatches) {
        this.userWatches = userWatches;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}
