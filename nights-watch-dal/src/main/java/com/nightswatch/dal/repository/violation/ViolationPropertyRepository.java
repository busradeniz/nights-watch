package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.ViolationProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationPropertyRepository extends JpaRepository<ViolationProperty, Long> {
}
