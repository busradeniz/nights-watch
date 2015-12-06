package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.ViolationGroup;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ViolationGroupRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ViolationGroupRepository violationGroupRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME");
        violationGroup = violationGroupRepository.save(violationGroup);
        assertNotNull(violationGroup);
        assertNotNull(violationGroup.getId());

        final ViolationGroup dbViolationGroup = violationGroupRepository.findOne(violationGroup.getId());
        assertEquals(violationGroup, dbViolationGroup);
    }

    @Test
    public void testSaveAndGetByName() throws Exception {
        ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME_1");
        violationGroup = violationGroupRepository.save(violationGroup);
        assertNotNull(violationGroup);
        assertNotNull(violationGroup.getId());

        final ViolationGroup dbViolationGroup = violationGroupRepository.findByName("TEST_VIOLATION_GROUP_NAME_1");
        assertEquals(violationGroup, dbViolationGroup);
    }
}