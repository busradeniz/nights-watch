package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.Tag;
import com.nightswatch.dal.repository.violation.TagRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.exception.IllegalTagNameException;
import com.nightswatch.service.violation.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class TagServiceImpl extends AbstractService<Tag, TagRepository> implements TagService {

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tag findOrCreate(String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            throw new IllegalTagNameException(tagName);
        }

        final String lowerCasedTagName = tagName.toLowerCase(Locale.forLanguageTag("TR"));
        Tag tag = repository.findByName(lowerCasedTagName);

        if (tag == null) {
            tag = new Tag();
            tag.setName(lowerCasedTagName);
            tag = repository.save(tag);
        }

        return tag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Tag> findAllOrCreate(String... tagNames) {
        final Collection<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            tags.add(this.findOrCreate(tagName));
        }
        return tags;
    }
}
