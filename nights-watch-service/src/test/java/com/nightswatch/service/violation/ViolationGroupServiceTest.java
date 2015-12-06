package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.ViolationGroup;
import com.nightswatch.dal.repository.violation.ViolationGroupRepository;
import com.nightswatch.service.exception.DataNotFoundException;
import com.nightswatch.service.violation.impl.ViolationGroupServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViolationGroupServiceTest {

    @Mock
    private ViolationGroupRepository violationGroupRepository;

    @InjectMocks
    private ViolationGroupServiceImpl violationGroupService;

    @Test
    public void testFindByName() throws Exception {
        final String violationGroupName = "TEST_VIOLATION_GROUP_NAME";
        final ViolationGroup expected = new ViolationGroup();
        expected.setName(violationGroupName);

        when(violationGroupRepository.findByName(violationGroupName))
                .thenReturn(expected);

        final ViolationGroup actual = violationGroupService.findByName(violationGroupName);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test(expected = DataNotFoundException.class)
    public void testFindByNameButNotFound() throws Exception {
        final String violationGroupName = "TEST_VIOLATION_GROUP_NAME";

        when(violationGroupRepository.findByName(violationGroupName))
                .thenReturn(null);

        violationGroupService.findByName(violationGroupName);
    }
}