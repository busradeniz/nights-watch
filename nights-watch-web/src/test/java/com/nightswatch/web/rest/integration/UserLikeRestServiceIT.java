package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.violation.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class UserLikeRestServiceIT extends AbstractIT {

    private static final Logger log = LoggerFactory.getLogger(UserLikeRestServiceIT.class);

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation icin Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Like yapilmasi
     */
    @Test
    public void testLikeViolation() throws Exception {
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

        // 4. Adim: Like Yapilmasi
        final UserLikeDto userLikeDto = new UserLikeDto();
        userLikeDto.setViolationId(createdViolationDto.getId());
        final UserLikeDto createdUserLikeDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userLike", userLikeDto, UserLikeDto.class);
        assertNotNull(createdUserLikeDto);
        assertNotNull(createdUserLikeDto.getId());
        assertNotNull("test", createdUserLikeDto.getUsername());

        final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(actualViolationDto);
        assertEquals(new Integer(1), actualViolationDto.getUserLikeCount());

        final Collection<UserLikeDto> userLikeDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userLikes", UserLikeDtoCollection.class);
        assertNotNull(userLikeDtos);
        assertEquals(createdUserLikeDto, userLikeDtos.iterator().next());

    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation icin Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Like yapilmasi
     * 5. Adim: Like'in silinmesi
     */
    @Test
    public void testUnlikeViolation() throws Exception {
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

        // 4. Adim: Like Yapilmasi
        final UserLikeDto userLikeDto = new UserLikeDto();
        userLikeDto.setViolationId(createdViolationDto.getId());
        final UserLikeDto createdUserLikeDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userLike", userLikeDto, UserLikeDto.class);
        assertNotNull(createdUserLikeDto);
        assertNotNull(createdUserLikeDto.getId());
        assertNotNull("test", createdUserLikeDto.getUsername());

        final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(actualViolationDto);
        assertEquals(new Integer(1), actualViolationDto.getUserLikeCount());

        final Collection<UserLikeDto> userLikeDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userLikes", UserLikeDtoCollection.class);
        assertNotNull(userLikeDtos);
        assertEquals(createdUserLikeDto, userLikeDtos.iterator().next());

        // 5. Adim: Like'in silinmesi
        this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/userLike/" + createdUserLikeDto.getId());
        final Collection<UserLikeDto> afterDeleteUserLikeDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userLikes", UserLikeDtoCollection.class);
        assertNotNull(afterDeleteUserLikeDtos);
        assertTrue(afterDeleteUserLikeDtos.isEmpty());

        final ViolationDto afterDeleteViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(afterDeleteViolationDto);
        assertEquals(new Integer(0), afterDeleteViolationDto.getUserLikeCount());


    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation icin Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Like yapilmasi
     * 5. Adim: Like'in silinmesi
     */
    @Test
    public void testUnlikeViolationMultipleTimes() throws Exception {
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

        for (int i = 0; i < 10; i++) {
            // 4. Adim: Like Yapilmasi
            final UserLikeDto userLikeDto = new UserLikeDto();
            userLikeDto.setViolationId(createdViolationDto.getId());
            final UserLikeDto createdUserLikeDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userLike", userLikeDto, UserLikeDto.class);
            assertNotNull(createdUserLikeDto);
            assertNotNull(createdUserLikeDto.getId());
            assertNotNull("test", createdUserLikeDto.getUsername());

            final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
            assertNotNull(actualViolationDto);
            assertEquals(new Integer(1), actualViolationDto.getUserLikeCount());

            final Collection<UserLikeDto> userLikeDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userLikes", UserLikeDtoCollection.class);
            assertNotNull(userLikeDtos);
            assertEquals(createdUserLikeDto, userLikeDtos.iterator().next());

            // 5. Adim: Like'in silinmesi
            this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/userLike/" + createdUserLikeDto.getId());
            final Collection<UserLikeDto> afterDeleteUserLikeDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userLikes", UserLikeDtoCollection.class);
            assertNotNull(afterDeleteUserLikeDtos);
            assertTrue(afterDeleteUserLikeDtos.isEmpty());

            final ViolationDto afterDeleteViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
            assertNotNull(afterDeleteViolationDto);
            assertEquals(new Integer(0), afterDeleteViolationDto.getUserLikeCount());
            log.info("{}/10", i);
        }

    }

    static class UserLikeDtoCollection implements Collection<UserLikeDto> {

        private final Collection<UserLikeDto> userLikeDtos;

        public UserLikeDtoCollection() {
            this.userLikeDtos = new ArrayList<>();
        }

        @Override
        public int size() {
            return userLikeDtos.size();
        }

        @Override
        public boolean isEmpty() {
            return userLikeDtos.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return userLikeDtos.contains(o);
        }

        @Override
        public Iterator<UserLikeDto> iterator() {
            return userLikeDtos.iterator();
        }

        @Override
        public Object[] toArray() {
            return userLikeDtos.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return userLikeDtos.toArray(a);
        }

        @Override
        public boolean add(UserLikeDto userLikeDto) {
            return userLikeDtos.add(userLikeDto);
        }

        @Override
        public boolean remove(Object o) {
            return userLikeDtos.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return userLikeDtos.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends UserLikeDto> c) {
            return userLikeDtos.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return userLikeDtos.removeAll(c);
        }

        @Override
        public boolean removeIf(Predicate<? super UserLikeDto> filter) {
            return userLikeDtos.removeIf(filter);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return userLikeDtos.retainAll(c);
        }

        @Override
        public void clear() {
            userLikeDtos.clear();
        }

        @Override
        public boolean equals(Object o) {
            return userLikeDtos.equals(o);
        }

        @Override
        public int hashCode() {
            return userLikeDtos.hashCode();
        }

        @Override
        public Spliterator<UserLikeDto> spliterator() {
            return userLikeDtos.spliterator();
        }

        @Override
        public Stream<UserLikeDto> stream() {
            return userLikeDtos.stream();
        }

        @Override
        public Stream<UserLikeDto> parallelStream() {
            return userLikeDtos.parallelStream();
        }

        @Override
        public void forEach(Consumer<? super UserLikeDto> action) {
            userLikeDtos.forEach(action);
        }
    }
}
