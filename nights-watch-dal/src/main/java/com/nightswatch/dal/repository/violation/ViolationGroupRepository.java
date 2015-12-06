package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.ViolationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationGroupRepository extends JpaRepository<ViolationGroup, Long> {

    ViolationGroup findByName(final String name);
}
