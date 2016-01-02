package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.ConstraintType;
import com.nightswatch.dal.entity.violation.ViolationGroup;
import com.nightswatch.dal.entity.violation.ViolationProperty;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ViolationPropertyRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ViolationPropertyRepository violationPropertyRepository;

    @Autowired
    private ViolationGroupRepository violationGroupRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP");
        violationGroupRepository.save(violationGroup);

        ViolationProperty violationProperty = new ViolationProperty();
        violationProperty.setViolationGroup(violationGroup);
        violationProperty.setConstraintType(ConstraintType.BOOL);
        violationProperty.setConstraintValue("true");
        violationProperty.setDescription("TEST PROPERTY");
        violationProperty.setProperty("my_new_property");
        violationProperty = violationPropertyRepository.save(violationProperty);
        assertNotNull(violationProperty);
        assertNotNull(violationProperty.getId());

        final ViolationProperty dbViolationProperty = this.violationPropertyRepository.findOne(violationProperty.getId());
        assertNotNull(dbViolationProperty);
        assertEquals(violationProperty, dbViolationProperty);

    }
}