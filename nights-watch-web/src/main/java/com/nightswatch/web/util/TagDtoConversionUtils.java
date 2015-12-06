package com.nightswatch.web.util;

import com.nightswatch.dal.entity.violation.Tag;

import java.util.ArrayList;
import java.util.Collection;

public final class TagDtoConversionUtils {

    private TagDtoConversionUtils() {
    }

    public static String convert(Tag tag) {
        return tag.getName();
    }

    public static Collection<String> convert(final Collection<Tag> tags) {
        final Collection<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(TagDtoConversionUtils.convert(tag));
        }

        return tagNames;
    }
}
