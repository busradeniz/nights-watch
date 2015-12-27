package com.nightswatch.api.dto.violation;

import java.io.Serializable;

/**
 * Violation requires more than one DTO for CRUD and List operations. These DTO
 * should extends this class
 */
public abstract class AbstractViolationDto implements Serializable {

    protected String title;
    protected String description;
    protected Double latitude;
    protected Double longitude;
    protected String address;
    protected ViolationStatusTypeDto violationStatus;
    protected DangerLevelTypeDto dangerLevel;
    protected FrequencyLevelTypeDto frequencyLevel;
    protected String violationGroupName;
    protected String customProperties;


    // Default Const.
    public AbstractViolationDto() {
    }

    // Copy Const.
    public AbstractViolationDto(AbstractViolationDto other) {
        this.title = other.title;
        this.description = other.description;
        this.latitude = other.latitude;
        this.longitude = other.longitude;
        this.address = other.address;
        this.violationStatus = other.violationStatus;
        this.dangerLevel = other.dangerLevel;
        this.frequencyLevel = other.frequencyLevel;
        this.violationGroupName = other.violationGroupName;
        this.customProperties = other.customProperties;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getViolationGroupName() {
        return violationGroupName;
    }

    public void setViolationGroupName(String violationGroupName) {
        this.violationGroupName = violationGroupName;
    }

    public String getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(String customProperties) {
        this.customProperties = customProperties;
    }
}
