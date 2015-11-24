package com.nightswatch.api.dto;

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
    //    private ViolationStatusTypeDto violationStatusTypeDto;
//    private DangerLevelTypeDto dangerLevelTypeDto;
//    private FrequencyLevelTypeDto frequencyLevelTypeDto;
    private String violationGroupName;
    private Collection<String> tags;

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
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
}
