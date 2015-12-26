package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.violation.ViolationGroupDto;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ViolationGroupRestServiceIT extends AbstractIT {

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation Grouplarin cekilmesi
     */
    @Test
    public void testGetViolationGroup() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Violation Grouplarin cekilmesi
        final Collection<ViolationGroupDto> violationGroupDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup", ViolationGroupDtoCollection.class);
        assertNotNull(violationGroupDtos);
        assertFalse(violationGroupDtos.isEmpty());

        final ViolationGroupDto violationGroupDto = violationGroupDtos.iterator().next();
        assertNotNull(violationGroupDto);
        assertNotNull(violationGroupDto.getViolationPropertyDtos());
        assertFalse(violationGroupDto.getViolationPropertyDtos().isEmpty());

    }

    static class ViolationGroupDtoCollection implements Collection<ViolationGroupDto> {
        private final Collection<ViolationGroupDto> violationGroupDtos;

        ViolationGroupDtoCollection() {
            violationGroupDtos = new ArrayList<>();
        }

        @Override
        public int size() {
            return violationGroupDtos.size();
        }

        @Override
        public boolean isEmpty() {
            return violationGroupDtos.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return violationGroupDtos.contains(o);
        }

        @Override
        public Iterator<ViolationGroupDto> iterator() {
            return violationGroupDtos.iterator();
        }

        @Override
        public Object[] toArray() {
            return violationGroupDtos.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return violationGroupDtos.toArray(a);
        }

        @Override
        public boolean add(ViolationGroupDto violationGroupDto) {
            return violationGroupDtos.add(violationGroupDto);
        }

        @Override
        public boolean remove(Object o) {
            return violationGroupDtos.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return violationGroupDtos.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends ViolationGroupDto> c) {
            return violationGroupDtos.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return violationGroupDtos.removeAll(c);
        }

        @Override
        public boolean removeIf(Predicate<? super ViolationGroupDto> filter) {
            return violationGroupDtos.removeIf(filter);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return violationGroupDtos.retainAll(c);
        }

        @Override
        public void clear() {
            violationGroupDtos.clear();
        }

        @Override
        public boolean equals(Object o) {
            return violationGroupDtos.equals(o);
        }

        @Override
        public int hashCode() {
            return violationGroupDtos.hashCode();
        }

        @Override
        public Spliterator<ViolationGroupDto> spliterator() {
            return violationGroupDtos.spliterator();
        }

        @Override
        public Stream<ViolationGroupDto> stream() {
            return violationGroupDtos.stream();
        }

        @Override
        public Stream<ViolationGroupDto> parallelStream() {
            return violationGroupDtos.parallelStream();
        }
    }

}
