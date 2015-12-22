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
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CommentRestServiceIT extends AbstractIT {

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation icin Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Comment icin gerekli media'larin yuklenmesi
     * 5. Adim: Comment Eklenmesi
     */
    @Test
    public void testCreateViolation() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Violation icin Gerekli Media'larin yuklenmesi
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

        // Bu admin sonunda violationMediaId'yi aliyoruz, birazdan bu id'yi violation yaratmada kullanacagiz
        final Long violationMediaId = mediaDto.getId();


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
        createViolationRequestDto.setMedias(Collections.singleton(violationMediaId));

        final ViolationDto createdViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation", createViolationRequestDto, ViolationDto.class);
        assertNotNull(createdViolationDto);
        assertThat(createdViolationDto.getMedias(), Matchers.contains(mediaDto));
        assertThat(createdViolationDto.getTags(), Matchers.contains("tag1", "tag2"));
        assertEquals("Test Violation Group", createdViolationDto.getViolationGroupName());
        // Authentication token'imiza iliskin kullanici otomatik olarak owner oluyor.
        // Authentication token'imizi bu kullaniciyi kullanarak aldik.
        assertEquals("test", createdViolationDto.getOwner());

        // 4. Adim: Comment icin Media'larin yaratilmasi
        final LinkedMultiValueMap<String, Object> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.add("file", new ClassPathResource("know_nothing_2.jpg"));
        parameterMap.add("mediaType", "IMAGE");
        final HttpHeaders newMediaHeaders = new HttpHeaders();
        newMediaHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        final HttpEntity<LinkedMultiValueMap<String, Object>> newRequestEntity = new HttpEntity<>(parameterMap, newMediaHeaders);
        final MediaDto newMediaDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/media/upload", newRequestEntity, MediaDto.class);
        assertNotNull(newMediaDto);
        assertNotNull(newMediaDto.getId());
        assertEquals("know_nothing_2.jpg", newMediaDto.getFileName());

        // Bu admin sonunda commentMediaId'yi aliyoruz, birazdan bu id'yi violation yaratmada kullanacagiz
        final Long commentMediaId = newMediaDto.getId();

        // 5. Adim: Comment'in yaratilmasi
        final CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setViolationId(createdViolationDto.getId());
        createCommentDto.setMediaIds(Collections.singletonList(commentMediaId));
        createCommentDto.setContent("TEST_COMMENT");

        final CommentDto createdCommentDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/comment", createCommentDto, CommentDto.class);
        assertNotNull(createdCommentDto);
        assertNotNull(createdCommentDto.getId());
        assertEquals("TEST_COMMENT", createdCommentDto.getContent());
        assertEquals("test", createdCommentDto.getUsername());

        final CommentDto commentDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/comment/" + createdCommentDto.getId(), CommentDto.class);
        assertNotNull(commentDto);
        assertEquals(createdCommentDto, commentDto);

        final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(actualViolationDto);
        assertEquals(new Integer(1), actualViolationDto.getCommentCount());

        final Collection<CommentDto> commentDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/comments", CommentDtoCollection.class);
        assertNotNull(commentDtos);
        assertEquals(commentDto, commentDtos.iterator().next());

    }

    /**
     * Just for test purposes --- This fixes some problems about reflection
     */
    static class CommentDtoCollection implements Collection<CommentDto> {
        private final Collection<CommentDto> commentDtos;

        public CommentDtoCollection() {
            this.commentDtos = new ArrayList<>();
        }

        @Override
        public int size() {
            return commentDtos.size();
        }

        @Override
        public boolean isEmpty() {
            return commentDtos.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return commentDtos.contains(o);
        }

        @Override
        public Iterator<CommentDto> iterator() {
            return commentDtos.iterator();
        }

        @Override
        public Object[] toArray() {
            return commentDtos.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return commentDtos.toArray(a);
        }

        @Override
        public boolean add(CommentDto commentDto) {
            return commentDtos.add(commentDto);
        }

        @Override
        public boolean remove(Object o) {
            return commentDtos.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return commentDtos.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends CommentDto> c) {
            return commentDtos.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return commentDtos.removeAll(c);
        }

        @Override
        public boolean removeIf(Predicate<? super CommentDto> filter) {
            return commentDtos.removeIf(filter);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return commentDtos.retainAll(c);
        }

        @Override
        public void clear() {
            commentDtos.clear();
        }

        @Override
        public boolean equals(Object o) {
            return commentDtos.equals(o);
        }

        @Override
        public int hashCode() {
            return commentDtos.hashCode();
        }

        @Override
        public Spliterator<CommentDto> spliterator() {
            return commentDtos.spliterator();
        }

        @Override
        public Stream<CommentDto> stream() {
            return commentDtos.stream();
        }

        @Override
        public Stream<CommentDto> parallelStream() {
            return commentDtos.parallelStream();
        }

        @Override
        public void forEach(Consumer<? super CommentDto> action) {
            commentDtos.forEach(action);
        }
    }
}
