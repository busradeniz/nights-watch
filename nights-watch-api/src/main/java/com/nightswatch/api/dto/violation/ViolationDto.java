package com.nightswatch.api.dto.violation;

import com.nightswatch.api.dto.EntityDto;
import com.nightswatch.api.dto.MediaDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ViolationDto implements EntityDto {

    private Long id;
    private String title;
    private Date violationDate;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private ViolationStatusTypeDto violationStatus;
    private DangerLevelTypeDto dangerLevel;
    private FrequencyLevelTypeDto frequencyLevel;
    private String violationGroupName;
    private Collection<String> tags;
    private Collection<MediaDto> medias;
    private Collection<String> userLikes;
    private Long comments;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getViolationGroupName() {
        return violationGroupName;
    }

    public void setViolationGroupName(String violationGroupName) {
        this.violationGroupName = violationGroupName;
    }

    public Collection<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public ViolationStatusTypeDto getViolationStatus() {
        return violationStatus;
    }

    public void setViolationStatus(ViolationStatusTypeDto violationStatus) {
        this.violationStatus = violationStatus;
    }

    public DangerLevelTypeDto getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(DangerLevelTypeDto dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public FrequencyLevelTypeDto getFrequencyLevel() {
        return frequencyLevel;
    }

    public void setFrequencyLevel(FrequencyLevelTypeDto frequencyLevel) {
        this.frequencyLevel = frequencyLevel;
    }

    public Collection<MediaDto> getMedias() {
        if (medias == null) {
            medias = new ArrayList<>();
        }
        return medias;
    }

    public void setMedias(Collection<MediaDto> medias) {
        this.medias = medias;
    }

    public Collection<String> getUserLikes() {
        if (userLikes == null) {
            userLikes = new ArrayList<>();
        }
        return userLikes;
    }

    public void setUserLikes(Collection<String> userLikes) {
        this.userLikes = userLikes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }
}
