package com.nightswatch.dal.repository;

import com.nightswatch.dal.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
