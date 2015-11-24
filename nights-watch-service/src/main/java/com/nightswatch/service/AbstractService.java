package com.nightswatch.service;

import com.nightswatch.dal.entity.AbstractEntity;
import com.nightswatch.service.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Almost all of the services have CRUD operations and those operations are just delegation to
 * the Entity repository. This class ensures all of services uses same implementation. Also provides generic operations
 * to rid of repetition
 */
public abstract class AbstractService<E extends AbstractEntity, R extends JpaRepository<E, Long>> implements CrudService<E> {

    public final static int DEFAULT_PAGE_SIZE = 100;

    protected final R repository;

    protected AbstractService(final R repository) {
        this.repository = repository;
    }

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public E save(E entity) {
        return repository.save(entity);
    }

    /**
     * Saves all the entities that are given
     *
     * @param entities
     * @return saved instances
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Collection<E> save(Iterable<E> entities) {
        return repository.save(entities);
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public E findOne(Long id) throws DataNotFoundException {
        final E entity = repository.findOne(id);

        if (entity == null) {
            throw new DataNotFoundException();
        }
        return entity;
    }

    /**
     * Returns all of the entities
     *
     * @return a List of entities
     */
    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param index page index
     * @return a page of entities
     */
    @Override
    public List<E> findAll(final int index) {
        final Page<E> page = repository.findAll(this.createPageable(index));
        return page.hasContent() ? page.getContent() : Collections.<E>emptyList();
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void delete(Long id) {
        repository.delete(id);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void delete(E entity) {
        repository.delete(entity);
    }

    /**
     * Creates Pageable to execute pageable query through repositories
     *
     * @param index of page
     * @return page information for queries
     */
    protected Pageable createPageable(final Integer index) {
        if (index == null) {
            return null;
        } else {
            return new PageRequest(index, DEFAULT_PAGE_SIZE);
        }
    }
}
