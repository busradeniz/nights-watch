package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<Violation, Long> {
}
