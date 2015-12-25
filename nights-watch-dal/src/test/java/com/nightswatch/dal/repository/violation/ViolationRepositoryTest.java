package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.*;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import com.nightswatch.dal.repository.user.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ViolationRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViolationGroupRepository violationGroupRepository;

    @Autowired
    private UserWatchRepository userWatchRepository;

    @Autowired
    private UserLikeRepository userLikeRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        final User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME");
        violationGroupRepository.save(violationGroup);


        Violation violation = new Violation();
        violation.setTitle("TEST_VIOLATION");
        violation.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation.setDangerLevelType(DangerLevelType.HIGH);
        violation.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation.setAddress("TEST_ADDRESS");
        violation.setDescription("TEST_DESCRIPTION");
        violation.setLastModifiedBy("LAST_MODIFIED_USER");
        violation.setLastModifiedDate(new Date());
        violation.setLatitude(0d);
        violation.setLongitude(0d);
        violation.setViolationDate(new Date());
        violation.setOwner(user);
        violation.setViolationGroup(violationGroup);
        violation = violationRepository.save(violation);
        assertNotNull(violation);
        assertNotNull(violation.getId());

        final Violation dbViolation = violationRepository.findOne(violation.getId());
        assertEquals(violation, dbViolation);

    }

    @Test
    public void testFindAllByOwnerAndViolationStatusType() throws Exception {
        final User user = new User();
        user.setEmail("test2@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test2");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_2");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME_2");
        violationGroupRepository.save(violationGroup);


        Violation violation = new Violation();
        violation.setTitle("TEST_VIOLATION_2");
        violation.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation.setDangerLevelType(DangerLevelType.HIGH);
        violation.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation.setAddress("TEST_ADDRESS");
        violation.setDescription("TEST_DESCRIPTION");
        violation.setLastModifiedBy("LAST_MODIFIED_USER");
        violation.setLastModifiedDate(new Date());
        violation.setLatitude(0d);
        violation.setLongitude(0d);
        violation.setViolationDate(new Date());
        violation.setOwner(user);
        violation.setViolationGroup(violationGroup);
        violation = violationRepository.save(violation);
        assertNotNull(violation);
        assertNotNull(violation.getId());

        final List<Violation> allByOwnerAndViolationStatusType = violationRepository.findAllByOwnerAndViolationStatusType(user, ViolationStatusType.NOT_VIOLATION);
        assertNotNull(allByOwnerAndViolationStatusType);
        assertFalse(allByOwnerAndViolationStatusType.isEmpty());
        assertEquals(violation, allByOwnerAndViolationStatusType.get(0));

    }

    @Test
    public void testFindAllWatchedViolations() throws Exception {
        final User user = new User();
        user.setEmail("test3.1@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test3.1");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_3.1");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME_3");
        violationGroupRepository.save(violationGroup);


        Violation violation = new Violation();
        violation.setTitle("TEST_VIOLATION_3");
        violation.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation.setDangerLevelType(DangerLevelType.HIGH);
        violation.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation.setAddress("TEST_ADDRESS");
        violation.setDescription("TEST_DESCRIPTION");
        violation.setLastModifiedBy("LAST_MODIFIED_USER");
        violation.setLastModifiedDate(new Date());
        violation.setLatitude(0d);
        violation.setLongitude(0d);
        violation.setViolationDate(new Date());
        violation.setOwner(user);
        violation.setViolationGroup(violationGroup);
        violation = violationRepository.save(violation);
        assertNotNull(violation);
        assertNotNull(violation.getId());

        final User user2 = new User();
        user2.setEmail("test3.2@gmail.com");
        user2.setPassword("1q2w3e");
        user2.setUsername("test3.2");
        final Role role2 = new Role();
        role2.setRoleName("TEST_ROLE_3.2");
        user2.setRoles(Collections.singletonList(role2));
        userRepository.save(user2);

        final UserWatch userWatch = new UserWatch();
        userWatch.setUser(user2);
        userWatch.setViolation(violation);
        userWatchRepository.save(userWatch);

        final List<Violation> allByOwnerAndViolationStatusType = violationRepository.findAllWatchedViolations(user2, ViolationStatusType.NOT_VIOLATION);
        assertNotNull(allByOwnerAndViolationStatusType);
        assertFalse(allByOwnerAndViolationStatusType.isEmpty());
        assertEquals(violation, allByOwnerAndViolationStatusType.get(0));
    }

    @Test
    public void testFindAllWithMostLikes() throws Exception {

        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME_4");
        violationGroupRepository.save(violationGroup);

        final User user = new User();
        user.setEmail("test4.1@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test4.1");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_4.1");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        Violation violation1 = new Violation();
        violation1.setTitle("TEST_VIOLATION_4.1");
        violation1.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation1.setDangerLevelType(DangerLevelType.HIGH);
        violation1.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation1.setAddress("TEST_ADDRESS");
        violation1.setDescription("TEST_DESCRIPTION");
        violation1.setLastModifiedBy("LAST_MODIFIED_USER");
        violation1.setLastModifiedDate(new Date());
        violation1.setLatitude(0d);
        violation1.setLongitude(0d);
        violation1.setViolationDate(new Date());
        violation1.setOwner(user);
        violation1.setViolationGroup(violationGroup);
        violation1 = violationRepository.save(violation1);
        assertNotNull(violation1);
        assertNotNull(violation1.getId());

        Violation violation2 = new Violation();
        violation2.setTitle("TEST_VIOLATION_4.2");
        violation2.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation2.setDangerLevelType(DangerLevelType.HIGH);
        violation2.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation2.setAddress("TEST_ADDRESS");
        violation2.setDescription("TEST_DESCRIPTION");
        violation2.setLastModifiedBy("LAST_MODIFIED_USER");
        violation2.setLastModifiedDate(new Date());
        violation2.setLatitude(0d);
        violation2.setLongitude(0d);
        violation2.setViolationDate(new Date());
        violation2.setOwner(user);
        violation2.setViolationGroup(violationGroup);
        violation2 = violationRepository.save(violation2);
        assertNotNull(violation2);
        assertNotNull(violation2.getId());

        final User user2 = new User();
        user2.setEmail("test4.2@gmail.com");
        user2.setPassword("1q2w3e");
        user2.setUsername("test4.2");
        final Role role2 = new Role();
        role2.setRoleName("TEST_ROLE_4.2");
        user2.setRoles(Collections.singletonList(role2));
        userRepository.save(user2);

        final UserLike userLike = new UserLike();
        userLike.setUser(user2);
        userLike.setViolation(violation2);
        userLike.setLikeDate(new Date());
        userLikeRepository.save(userLike);

        final List<Violation> allByOwnerAndViolationStatusType = violationRepository.findAllWithMostLikes();
        assertNotNull(allByOwnerAndViolationStatusType);
        assertFalse(allByOwnerAndViolationStatusType.isEmpty());
        assertEquals(violation2, allByOwnerAndViolationStatusType.get(0));
    }

    @Test
    public void testFindAllWithMostLikesButNoLikes() throws Exception {

        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME_4");
        violationGroupRepository.save(violationGroup);

        final User user = new User();
        user.setEmail("test4.1@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test4.1");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_4.1");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        Violation violation1 = new Violation();
        violation1.setTitle("TEST_VIOLATION_4.1");
        violation1.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation1.setDangerLevelType(DangerLevelType.HIGH);
        violation1.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation1.setAddress("TEST_ADDRESS");
        violation1.setDescription("TEST_DESCRIPTION");
        violation1.setLastModifiedBy("LAST_MODIFIED_USER");
        violation1.setLastModifiedDate(new Date());
        violation1.setLatitude(0d);
        violation1.setLongitude(0d);
        violation1.setViolationDate(new Date());
        violation1.setOwner(user);
        violation1.setViolationGroup(violationGroup);
        violation1 = violationRepository.save(violation1);
        assertNotNull(violation1);
        assertNotNull(violation1.getId());

        final List<Violation> allByOwnerAndViolationStatusType = violationRepository.findAllWithMostLikes();
        assertNotNull(allByOwnerAndViolationStatusType);
        assertFalse(allByOwnerAndViolationStatusType.isEmpty());
        assertEquals(violation1, allByOwnerAndViolationStatusType.get(0));
    }
}