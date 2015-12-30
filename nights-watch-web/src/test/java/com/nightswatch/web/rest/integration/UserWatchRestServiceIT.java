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

public class UserWatchRestServiceIT extends AbstractIT {

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation icin Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Watch yapilmasi
     */
    @Test
    public void testWatchViolation() throws Exception {
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

        // 4. Adim: Watch Yapilmasi
        final UserWatchDto userWatchDto = new UserWatchDto();
        userWatchDto.setViolationId(createdViolationDto.getId());
        final UserWatchDto createdUserWatchDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userWatch", userWatchDto, UserWatchDto.class);
        assertNotNull(createdUserWatchDto);
        assertNotNull(createdUserWatchDto.getId());
        assertNotNull("test", createdUserWatchDto.getUsername());

        final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(actualViolationDto);
        assertEquals(new Integer(1), actualViolationDto.getUserWatchCount());

        final Collection<UserWatchDto> userWatchDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userWatches", UserWatchDtoCollection.class);
        assertNotNull(userWatchDtos);
        assertEquals(createdUserWatchDto, userWatchDtos.iterator().next());

    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Violation icin Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Watch yapilmasi
     * 5. Adim: Watch'in silinmesi
     */
    @Test
    public void testUnwatchViolation() throws Exception {
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

        // 4. Adim: Watch Yapilmasi
        final UserWatchDto userWatchDto = new UserWatchDto();
        userWatchDto.setViolationId(createdViolationDto.getId());
        final UserWatchDto createdUserWatchDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userWatch", userWatchDto, UserWatchDto.class);
        assertNotNull(createdUserWatchDto);
        assertNotNull(createdUserWatchDto.getId());
        assertNotNull("test", createdUserWatchDto.getUsername());


        // 5. Adim: Watch'in silinmesi
        this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/userWatch/" + createdUserWatchDto.getId());

        final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(actualViolationDto);
        assertEquals(new Integer(0), actualViolationDto.getUserWatchCount());

        final Collection<UserWatchDto> userWatchDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userWatches", UserWatchDtoCollection.class);
        assertNotNull(userWatchDtos);
        assertTrue(userWatchDtos.isEmpty());

    }

    @Test
    public void testUnwatchViolationMultipleTimes() throws Exception {
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
            // 4. Adim: Watch Yapilmasi
            final UserWatchDto userWatchDto = new UserWatchDto();
            userWatchDto.setViolationId(createdViolationDto.getId());
            final UserWatchDto createdUserWatchDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userWatch", userWatchDto, UserWatchDto.class);
            assertNotNull(createdUserWatchDto);
            assertNotNull(createdUserWatchDto.getId());
            assertNotNull("test", createdUserWatchDto.getUsername());


            // 5. Adim: Watch'in silinmesi
            this.getSecureTemplate(signInResponseDto.getToken()).delete(this.baseUrl + "/userWatch/" + createdUserWatchDto.getId());

            final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
            assertNotNull(actualViolationDto);
            assertEquals(new Integer(0), actualViolationDto.getUserWatchCount());

            final Collection<UserWatchDto> userWatchDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/userWatches", UserWatchDtoCollection.class);
            assertNotNull(userWatchDtos);
            assertTrue(userWatchDtos.isEmpty());
        }

    }

    static class UserWatchDtoCollection implements Collection<UserWatchDto> {
        private final Collection<UserWatchDto> userWatchDtos;

        public UserWatchDtoCollection() {
            this.userWatchDtos = new ArrayList<>();
        }

        @Override
        public int size() {
            return userWatchDtos.size();
        }

        @Override
        public boolean isEmpty() {
            return userWatchDtos.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return userWatchDtos.contains(o);
        }

        @Override
        public Iterator<UserWatchDto> iterator() {
            return userWatchDtos.iterator();
        }

        @Override
        public Object[] toArray() {
            return userWatchDtos.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return userWatchDtos.toArray(a);
        }

        @Override
        public boolean add(UserWatchDto userWatchDto) {
            return userWatchDtos.add(userWatchDto);
        }

        @Override
        public boolean remove(Object o) {
            return userWatchDtos.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return userWatchDtos.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends UserWatchDto> c) {
            return userWatchDtos.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return userWatchDtos.removeAll(c);
        }

        @Override
        public boolean removeIf(Predicate<? super UserWatchDto> filter) {
            return userWatchDtos.removeIf(filter);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return userWatchDtos.retainAll(c);
        }

        @Override
        public void clear() {
            userWatchDtos.clear();
        }

        @Override
        public boolean equals(Object o) {
            return userWatchDtos.equals(o);
        }

        @Override
        public int hashCode() {
            return userWatchDtos.hashCode();
        }

        @Override
        public Spliterator<UserWatchDto> spliterator() {
            return userWatchDtos.spliterator();
        }

        @Override
        public Stream<UserWatchDto> stream() {
            return userWatchDtos.stream();
        }

        @Override
        public Stream<UserWatchDto> parallelStream() {
            return userWatchDtos.parallelStream();
        }

        @Override
        public void forEach(Consumer<? super UserWatchDto> action) {
            userWatchDtos.forEach(action);
        }
    }
}
