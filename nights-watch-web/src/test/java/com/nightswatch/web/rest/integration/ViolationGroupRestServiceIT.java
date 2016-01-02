package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.violation.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.*;

public class ViolationGroupRestServiceIT extends AbstractIT {

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation Grouplarin cekilmesi
     */
    @Test
    public void testGetViolationGroups() throws Exception {
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

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation Grubun cekilmesi
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

        // 2. Adim: Violation Groubun cekilmesi
        final ViolationGroupDto violationGroupDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup/1", ViolationGroupDto.class);
        assertNotNull(violationGroupDto);
        assertNotNull(violationGroupDto.getViolationPropertyDtos());
        assertFalse(violationGroupDto.getViolationPropertyDtos().isEmpty());

    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation Grubun yaratilmasi
     * 3. Adim: Violation Grubun guncellenmesi
     * 4. Adim: Violation Grubun silinmesi
     */
    @Test
    public void testCreateUpdateAndDeleteViolationGroup() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Violation Grubun yaratilmasi
        final SimpleViolationGroupDto simpleViolationGroupDto = new SimpleViolationGroupDto();
        simpleViolationGroupDto.setName("My new test violation group");
        final ViolationGroupDto createdViolationGroupDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violationGroup", simpleViolationGroupDto, ViolationGroupDto.class);
        assertNotNull(createdViolationGroupDto);
        assertEquals("My new test violation group", createdViolationGroupDto.getName());
        assertNotNull(createdViolationGroupDto.getViolationPropertyDtos());
        assertTrue(createdViolationGroupDto.getViolationPropertyDtos().isEmpty());

        // 3. Adim: Violation Grubun guncellenmesi
        final SimpleViolationGroupDto updateViolationGroup = new SimpleViolationGroupDto();
        updateViolationGroup.setId(createdViolationGroupDto.getId());
        updateViolationGroup.setName("My new test violation group 2");

        this.getSecureTemplate(signInResponseDto.getToken()).put(this.baseUrl + "/violationGroup/" + createdViolationGroupDto.getId(), updateViolationGroup);
        final ViolationGroupDto updatedViolationGroupDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup/" + createdViolationGroupDto.getId(), ViolationGroupDto.class);
        assertNotNull(updatedViolationGroupDto);
        assertEquals("My new test violation group 2", updatedViolationGroupDto.getName());
        assertNotNull(updatedViolationGroupDto.getViolationPropertyDtos());
        assertTrue(updatedViolationGroupDto.getViolationPropertyDtos().isEmpty());

        // 3. Adim: Violation Grubun silinmesi
        this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/violationGroup/" + createdViolationGroupDto.getId(), updateViolationGroup);

        try {
            final ViolationGroupDto deletedViolationGroupDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup/" + createdViolationGroupDto.getId(), ViolationGroupDto.class);
            fail("Violation Group is deleted! It should not be found");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }

    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Violation Groupbun silinmesi
     */
    @Test
    public void testDeleteViolationGroupThatHasViolation() throws Exception {
        final String testViolationGroupName = "Test Violation Group";
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Gerekli Media'larin yuklenmesi
        final RestTemplate secureRestTemplate = this.getSecureTemplate(signInResponseDto.getToken());

        final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource("know_nothing.jpg"));
        map.add("mediaType", "IMAGE");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        final MediaDto mediaDto = secureRestTemplate.postForObject(this.baseUrl + "/media/upload", requestEntity, MediaDto.class);
        assertNotNull(mediaDto);
        assertNotNull(mediaDto.getId());
        assertEquals("know_nothing.jpg", mediaDto.getFileName());

        // Bu admin sonunda mediaId'yi aliyoruz, birazdan bu id'yi violation yaratmada kullanacagiz
        final Long mediaId = mediaDto.getId();


        // 3. Adim: Violation'in yaratilmasi
        final CreateViolationRequestDto createViolationRequestDto = new CreateViolationRequestDto();
        createViolationRequestDto.setTitle("Test Violation");
        createViolationRequestDto.setDescription("Test Violation for Integration Test");
        createViolationRequestDto.setLatitude(0d);
        createViolationRequestDto.setLongitude(0d);
        createViolationRequestDto.setAddress("Right here!!");
        createViolationRequestDto.setViolationStatus(ViolationStatusTypeDto.NEW);
        createViolationRequestDto.setDangerLevel(DangerLevelTypeDto.LOW);
        createViolationRequestDto.setFrequencyLevel(FrequencyLevelTypeDto.LOW);
        // Dikkat!! Bu groubun veritabaninda olmasi gerekiyor. Ekranlar gelistirilirken
        // Combobox benzeri bir yapidan secilmeli. Combobox icerisi ViolationGroupRestService
        // icerisinden doldurulmali.
        createViolationRequestDto.setViolationGroupName("Test Violation Group");
        // Yine tagler TagRestService icerisinden secilmeli. Olmayan tagler otomatik olarak
        // yaratilacak. Tum tagler lower-cased olarak yaratilacak.
        createViolationRequestDto.setTags(Arrays.asList("tag1", "tag2"));
        // Az once yarattigimiz media'yi kullaniyoruz.
        createViolationRequestDto.setMedias(Collections.singleton(mediaId));

        final ViolationDto createdViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation", createViolationRequestDto, ViolationDto.class);
        assertNotNull(createdViolationDto);
        assertThat(createdViolationDto.getMedias(), Matchers.contains(mediaDto));
        assertThat(createdViolationDto.getTags(), Matchers.contains("tag1", "tag2"));
        assertEquals("Test Violation Group", createdViolationDto.getViolationGroupName());
        // Authentication token'imiza iliskin kullanici otomatik olarak owner oluyor.
        // Authentication token'imizi bu kullaniciyi kullanarak aldik.
        assertEquals("test", createdViolationDto.getOwner());

        // 4. Adim Violation Groubun silinmesi

        final Collection<ViolationGroupDto> violationGroupDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violationGroup", ViolationGroupDtoCollection.class);
        assertNotNull(violationGroupDtos);
        assertFalse(violationGroupDtos.isEmpty());
        ViolationGroupDto violationGroupDto = null;
        for (ViolationGroupDto violationGroupDto1 : violationGroupDtos) {
            if (violationGroupDto1.getName().equals(testViolationGroupName)) {
                violationGroupDto = violationGroupDto1;
                break;
            }
        }
        assertNotNull(violationGroupDto);

        try {
            this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/violationGroup/" + violationGroupDto.getId());
            fail("Violation Group should not be deleted! It has violations");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }

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

    }

}
