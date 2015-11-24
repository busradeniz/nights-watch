package com.nightswatch.api.rest;

import com.nightswatch.api.dto.EntityDto;

/**
 * Rest service interface it contains all the CRUD operations for services
 */
public interface CrudRestService<T extends EntityDto> {

    String ID_PATH = "/{id}";

    /**
     * Creates a new entity for given DTO
     *
     * @param dto entity data
     * @return Created entity information with new assigned id
     */
    T create(final T dto, final String token);

    /**
     * Finds entities with given id and returns its information as DTO
     *
     * @param entityId existing unique entity id
     * @return existing entity as AccountDTO
     */
    T get(final Long entityId, final String token);

    /**
     * Updates entity information with data from entityDto. AccountId and entityId in the dto must match.
     *
     * @param entityId  existing unique entity id
     * @param entityDto entity information will be updated according to this data
     * @return updated version of entity as entity dto
     */
    T update(final Long entityId, final T entityDto, final String token);

    /**
     * This operation does not delete actual data.
     * Instead updates entity information and makes generalStatus of entity to Passive.
     *
     * @param entityId exiting unique entity id
     * @return updated version of entity as entity Dto
     */
    T delete(final Long entityId, final String token);
}
