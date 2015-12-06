package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
