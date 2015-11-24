package com.nightswatch.dal.entity;

import java.io.Serializable;

/**
 * All relational database entities must implements this interface, which represents minimum requirements to meet
 */
public interface Entity extends Serializable, Cloneable, Comparable<Entity> {

    /**
     * @return Id for entity
     */
    Long getId();

    /**
     * Sets entity id
     *
     * @param id for this entity
     */
    void setId(Long id);
}
