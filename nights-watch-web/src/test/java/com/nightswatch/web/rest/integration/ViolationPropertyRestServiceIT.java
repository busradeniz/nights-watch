package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.violation.ConstraintTypeDto;
import com.nightswatch.api.dto.violation.ViolationGroupDto;
import com.nightswatch.api.dto.violation.ViolationPropertyDto;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

public class ViolationPropertyRestServiceIT extends AbstractIT {

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation Grouplarin cekilmesi
     * 3. Adim: Yeni ViolationPropert'nin yaratilmasi
     */
    @Test
    public void testCreateViolationProperty() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Violation Grouplarin cekilmesi
        final Collection<ViolationGroupDto> violationGroupDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup", ViolationGroupRestServiceIT.ViolationGroupDtoCollection.class);
        assertNotNull(violationGroupDtos);
        assertFalse(violationGroupDtos.isEmpty());

        final ViolationGroupDto violationGroupDto = violationGroupDtos.iterator().next();
        assertNotNull(violationGroupDto);
        assertNotNull(violationGroupDto.getViolationPropertyDtos());
        assertFalse(violationGroupDto.getViolationPropertyDtos().isEmpty());

        // 3. Adim: Yeni ViolationPropert'nin yaratilmasi
        final ViolationPropertyDto violationPropertyDto = new ViolationPropertyDto();
        violationPropertyDto.setViolationGroupId(violationGroupDto.getId());
        violationPropertyDto.setProperty("my_test_property");
        violationPropertyDto.setDescription("Test Violation Property Dto");
        violationPropertyDto.setConstraintValue("true");
        violationPropertyDto.setConstraintTypeDto(ConstraintTypeDto.BOOL);

        final ViolationPropertyDto createdViolationPropertyDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violationProperty", violationPropertyDto, ViolationPropertyDto.class);
        assertNotNull(createdViolationPropertyDto);
        assertNotNull(createdViolationPropertyDto.getId());

        final ViolationGroupDto afterCreateViolationGroup = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup/" + violationGroupDto.getId(), ViolationGroupDto.class);
        assertNotNull(afterCreateViolationGroup);
        assertEquals(violationGroupDto.getViolationPropertyDtos().size() + 1, afterCreateViolationGroup.getViolationPropertyDtos().size());
        assertThat(afterCreateViolationGroup.getViolationPropertyDtos(), hasItem(createdViolationPropertyDto));

    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Tum violation propertylerin cekilmesi
     * 3. Adim: Violation Propertynin silinmesi
     */
    @Test
    public void testGetAndDeleteViolationProperty() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Tum violation propertylerin cekilmesi
        final Collection<ViolationPropertyDto> violationPropertyDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationProperty", ViolationPropertyDtoCollection.class);
        assertNotNull(violationPropertyDtos);
        assertFalse(violationPropertyDtos.isEmpty());

        // 3. Adim: Violation Propertynin silinmesi
        final ViolationPropertyDto goingToBeDeleted = violationPropertyDtos.iterator().next();
        this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/violationProperty/" + goingToBeDeleted.getId());

        final Collection<ViolationPropertyDto> afterDeleteViolationPropertyDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationProperty", ViolationPropertyDtoCollection.class);
        assertNotNull(afterDeleteViolationPropertyDtos);
        assertEquals(violationPropertyDtos.size() - 1, afterDeleteViolationPropertyDtos.size());
    }

    static class ViolationPropertyDtoCollection implements Collection<ViolationPropertyDto> {

        private final Collection<ViolationPropertyDto> violationPropertyDtos;

        ViolationPropertyDtoCollection() {
            violationPropertyDtos = new ArrayList<>();
        }

        @Override
        public int size() {
            return violationPropertyDtos.size();
        }

        @Override
        public boolean isEmpty() {
            return violationPropertyDtos.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return violationPropertyDtos.contains(o);
        }

        @Override
        public Iterator<ViolationPropertyDto> iterator() {
            return violationPropertyDtos.iterator();
        }

        @Override
        public Object[] toArray() {
            return violationPropertyDtos.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return violationPropertyDtos.toArray(a);
        }

        @Override
        public boolean add(ViolationPropertyDto violationPropertyDto) {
            return violationPropertyDtos.add(violationPropertyDto);
        }

        @Override
        public boolean remove(Object o) {
            return violationPropertyDtos.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return violationPropertyDtos.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends ViolationPropertyDto> c) {
            return violationPropertyDtos.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return violationPropertyDtos.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return violationPropertyDtos.retainAll(c);
        }

        @Override
        public void clear() {
            violationPropertyDtos.clear();
        }

        @Override
        public boolean equals(Object o) {
            return violationPropertyDtos.equals(o);
        }

        @Override
        public int hashCode() {
            return violationPropertyDtos.hashCode();
        }
    }

}
