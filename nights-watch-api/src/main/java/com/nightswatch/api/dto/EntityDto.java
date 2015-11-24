package com.nightswatch.api.dto;

import java.io.Serializable;

/**
 * If DTO object directly represents an entity, it should implement this interface.
 */
public interface EntityDto extends Serializable {

    Long getId();
}
