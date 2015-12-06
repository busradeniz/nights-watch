package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.Tag;
import com.nightswatch.service.CrudService;

import java.util.Collection;

public interface TagService extends CrudService<Tag> {

    Tag findOrCreate(final String tag);

    Collection<Tag> findAllOrCreate(final String... tags);
}
