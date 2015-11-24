package com.nightswatch.service;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.service.exception.DataNotFoundException;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

/**
 * Almost all of the services have CRUD operations and those operations are just delegation to
 * the Entity repository. This interface ensures that all of the crud operation are defined in a generic manner.
 */
public interface CrudService<E extends AbstractEntity> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    E save(E entity);

    /**
     * Saves all the entities that are given
     *
     * @param entities
     * @return saved instances
     */
    Collection<E> save(Iterable<E> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    E findOne(Long id) throws DataNotFoundException;

    /**
     * Returns all of the entities
     *
     * @return a List of entities
     */
    List<E> findAll();

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param index page index
     * @return a page of entities
     */
    List<E> findAll(int index);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Long id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(E entity);
}
