package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.dto.TagDto;
import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.violation.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ViolationRestServiceIT extends AbstractIT {

    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
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

        final ViolationDto actualViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(actualViolationDto);
        assertEquals(createdViolationDto, actualViolationDto);
    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Violation'in guncellenmesi
     * 5. Adim: Violation'a yeni media eklenmesi
     * 6. Adim: Violation'a yeni tag eklenmesi
     * 7. Adim: Violation'dan eski bir tag'in silinmesi
     * 8. Adim: Violation'dan eski bir media'nin silinmesi
     */
    @Test
    public void testCreateUpdateAddMediaAddTagRemoveMediaRemoveTagViolation() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        // 1. Adim: Authentication
        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        // 2. Adim: Gerekli Media'larin yuklenmesi
        final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource("know_nothing.jpg"));
        map.add("mediaType", "IMAGE");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        final MediaDto mediaDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/media/upload", requestEntity, MediaDto.class);
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
        createViolationRequestDto.setViolationGroupName("Test Violation Group");
        createViolationRequestDto.setTags(Arrays.asList("tag1", "tag2"));
        createViolationRequestDto.setMedias(Collections.singleton(mediaId));

        final ViolationDto createdViolationDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation", createViolationRequestDto, ViolationDto.class);
        assertNotNull(createdViolationDto);
        assertThat(createdViolationDto.getMedias(), Matchers.contains(mediaDto));
        assertThat(createdViolationDto.getTags(), Matchers.contains("tag1", "tag2"));
        assertEquals("Test Violation Group", createdViolationDto.getViolationGroupName());
        assertEquals("test", createdViolationDto.getOwner());
        assertEquals("Test Violation", createdViolationDto.getTitle());

        final ViolationDto afterCreated = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(afterCreated);
        assertEquals(createdViolationDto, afterCreated);

        // 4. Adim: Violation'in guncellenmesi
        // Yaratirken kullandigimiz ayni nesneyi kullanacagiz tek farki title'i degistirecegiz
        final UpdateViolationRequestDto updateViolationRequestDto = new UpdateViolationRequestDto(createdViolationDto);
        updateViolationRequestDto.setTitle("Test Violation 2");

        this.getSecureTemplate(signInResponseDto.getToken()).put(this.baseUrl + "/violation/" + createdViolationDto.getId(), updateViolationRequestDto);

        final ViolationDto afterUpdate = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/" + createdViolationDto.getId(), ViolationDto.class);
        assertNotNull(afterUpdate);
        assertEquals("Test Violation 2", afterUpdate.getTitle());

        // 5. Adim: Yeni Bir Media'in yuklenmesi
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

        final ViolationDto afterNewMediaAdded = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/addMedia", newMediaDto, ViolationDto.class);
        assertNotNull(afterNewMediaAdded);
        assertEquals(afterUpdate.getTitle(), afterNewMediaAdded.getTitle());
        assertEquals(2, afterNewMediaAdded.getMedias().size());

        // 6. Adim: Yeni Bir Tag'in eklenmesi
        final TagDto tagDto = new TagDto();
        tagDto.setTagName("tag3");
        final ViolationDto afterNewTagAdded = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/addTag", tagDto, ViolationDto.class);
        assertNotNull(afterNewTagAdded);
        assertEquals(afterUpdate.getTitle(), afterNewTagAdded.getTitle());
        assertEquals(3, afterNewTagAdded.getTags().size());

        // 7. Adim: Eski bir Tag'in silinmesi
        final TagDto removeTagDto = new TagDto();
        removeTagDto.setTagName("tag1");
        final ViolationDto afterNewTagRemoved = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/removeTag", removeTagDto, ViolationDto.class);
        assertNotNull(afterNewTagRemoved);
        assertEquals(afterUpdate.getTitle(), afterNewTagRemoved.getTitle());
        assertEquals(2, afterNewTagRemoved.getTags().size());

        // 8. Adim: Eski Bir Media'in silinmesi
        // know_nothing.jpg yani ilk eklenen media silinecek
        final ViolationDto afterOldMediaRemoved = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/violation/" + createdViolationDto.getId() + "/removeMedia", mediaDto, ViolationDto.class);
        assertNotNull(afterOldMediaRemoved);
        assertEquals(afterUpdate.getTitle(), afterOldMediaRemoved.getTitle());
        assertEquals(1, afterOldMediaRemoved.getMedias().size());
        assertEquals("know_nothing_2.jpg", afterOldMediaRemoved.getMedias().iterator().next().getFileName());
    }


    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: En cok like alan violationlarin okunmasi
     */
    @Test
    public void testGetMostLikedViolations() throws Exception {
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

        // 4. Adim: En cok like alan violationlarin okunmasi
        final Collection<ViolationDto> violationDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/top20/mostLiked", ViolationDtoList.class);
        assertNotNull(violationDtos);
        assertFalse(violationDtos.isEmpty());
    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: En yeni violationlarin okunmasi
     */
    @Test
    public void testGetNewestViolations() throws Exception {
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

        // 4. Adim: En yeni violationlarin okunmasi
        final Collection<ViolationDto> violationDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/top20/newest", ViolationDtoList.class);
        assertNotNull(violationDtos);
        assertFalse(violationDtos.isEmpty());
    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Sahip Olunan Violationlarin dogru durum koduyla okunmasi
     * 5. Adim: Sahip Olunan Violationlarin yanlis durum koduyla okunmasi
     */
    @Test
    public void testGetOwnedViolations() throws Exception {
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

        // 4. Adim: Sahip Olunan Violationlarin dogru durum koduyla okunmasi
        final Collection<ViolationDto> violationDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/top20/owned?violationStatus=NEW", ViolationDtoList.class);
        assertNotNull(violationDtos);
        assertFalse(violationDtos.isEmpty());

        // 5. Adim: Sahip Olunan Violationlarin yanlis durum koduyla okunmasi
        final Collection<ViolationDto> emptyDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/top20/owned?violationStatus=FIXED", ViolationDtoList.class);
        assertNotNull(emptyDtos);
        assertTrue(emptyDtos.isEmpty());

    }

    /**
     * 1. Adim: Authentication
     * 2. Adim: Gerekli Media'larin Yuklenmesi
     * 3. Adim: Violation'in yaratilmasi
     * 4. Adim: Violation'in takip edilmesi
     * 5. Adim: Takip Edilen Violation'larin okunmasi
     */
    @Test
    public void testGetWatchedViolations() throws Exception {
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

        // 4. Adim: Violation'in takip edilmesi
        final UserWatchDto userWatchDto = new UserWatchDto();
        userWatchDto.setViolationId(createdViolationDto.getId());
        final UserWatchDto createdUserWatchDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/userWatch", userWatchDto, UserWatchDto.class);
        assertNotNull(createdUserWatchDto);
        assertNotNull(createdUserWatchDto.getId());
        assertNotNull("test", createdUserWatchDto.getUsername());

        // 5. Adim: Takip Edilen Violation'larin okunmasi
        final Collection<ViolationDto> violationDtos = this.getSecureTemplate(signInResponseDto.getToken()).getForObject(this.baseUrl + "/violation/top20/watched?violationStatus=NEW", ViolationDtoList.class);
        assertNotNull(violationDtos);
        assertFalse(violationDtos.isEmpty());
    }

    static class ViolationDtoList implements Collection<ViolationDto> {

        private final Collection<ViolationDto> violationDtos;

        ViolationDtoList() {
            this.violationDtos = new ArrayList<>();
        }

        @Override
        public int size() {
            return violationDtos.size();
        }

        @Override
        public boolean isEmpty() {
            return violationDtos.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return violationDtos.contains(o);
        }

        @Override
        public Iterator<ViolationDto> iterator() {
            return violationDtos.iterator();
        }

        @Override
        public Object[] toArray() {
            return violationDtos.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return violationDtos.toArray(a);
        }

        @Override
        public boolean add(ViolationDto violationDto) {
            return violationDtos.add(violationDto);
        }

        @Override
        public boolean remove(Object o) {
            return violationDtos.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return violationDtos.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends ViolationDto> c) {
            return violationDtos.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return violationDtos.removeAll(c);
        }

        @Override
        public boolean removeIf(Predicate<? super ViolationDto> filter) {
            return violationDtos.removeIf(filter);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return violationDtos.retainAll(c);
        }

        @Override
        public void clear() {
            violationDtos.clear();
        }

        @Override
        public boolean equals(Object o) {
            return violationDtos.equals(o);
        }

        @Override
        public int hashCode() {
            return violationDtos.hashCode();
        }

        @Override
        public Spliterator<ViolationDto> spliterator() {
            return violationDtos.spliterator();
        }

        @Override
        public Stream<ViolationDto> stream() {
            return violationDtos.stream();
        }

        @Override
        public Stream<ViolationDto> parallelStream() {
            return violationDtos.parallelStream();
        }
    }
}
