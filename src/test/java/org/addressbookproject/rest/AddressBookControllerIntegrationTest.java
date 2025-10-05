package org.addressbookproject.rest;

import jakarta.transaction.Transactional;
import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.entity.BuddyInfo;
import org.addressbookproject.repository.AddressBookRepository;
import org.addressbookproject.repository.BuddyInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private RestTemplate restTemplate;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:" + port + "/api";

        restTemplate.postForEntity(baseUrl + "/reset", null, Void.class);
    }

    @Test
    public void testCreateAndGetAddressBooks() {
        ResponseEntity<AddressBook> createResponse = restTemplate.postForEntity(
                baseUrl + "/addressBook", null, AddressBook.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        AddressBook created = createResponse.getBody();
        System.out.print("created: " + created);
        assertNotNull(created);
        assertNotNull(created.getId());

        ResponseEntity<AddressBook[]> getResponse = restTemplate.getForEntity(
                baseUrl + "/addressBooks", AddressBook[].class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        AddressBook[] books = getResponse.getBody();
        assertNotNull(books);
        assertEquals(1, books.length);
        assertEquals(created.getId(), books[0].getId());
    }

    @Test
    public void testAddAndRetrieveBuddies() {
        ResponseEntity<AddressBook> createBook = restTemplate.postForEntity(
                baseUrl + "/addressBook", null, AddressBook.class);
        AddressBook book = createBook.getBody();
        assertNotNull(book);

        BuddyInfo buddy = new BuddyInfo("Alice", "123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BuddyInfo> request = new HttpEntity<>(buddy, headers);

        ResponseEntity<BuddyInfo> addResponse = restTemplate.postForEntity(
                baseUrl + "/addressBook/" + book.getId() + "/buddies", request, BuddyInfo.class);

        assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());
        BuddyInfo savedBuddy = addResponse.getBody();
        assertNotNull(savedBuddy);
        assertEquals("Alice", savedBuddy.getName());

        ResponseEntity<BuddyInfo[]> getResponse = restTemplate.getForEntity(
                baseUrl + "/addressBook/" + book.getId() + "/buddies", BuddyInfo[].class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        BuddyInfo[] buddies = getResponse.getBody();
        assertNotNull(buddies);
        assertEquals(1, buddies.length);
        assertEquals("Alice", buddies[0].getName());
    }

    @Test
    public void testRemoveBuddy() {
        ResponseEntity<AddressBook> createBook = restTemplate.postForEntity(
                baseUrl + "/addressBook", null, AddressBook.class);
        AddressBook book = createBook.getBody();
        assertNotNull(book);

        BuddyInfo buddy = new BuddyInfo("Bob", "987654");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BuddyInfo> request = new HttpEntity<>(buddy, headers);

        ResponseEntity<BuddyInfo> addResponse = restTemplate.postForEntity(
                baseUrl + "/addressBook/" + book.getId() + "/buddies", request, BuddyInfo.class);
        BuddyInfo savedBuddy = addResponse.getBody();
        assertNotNull(savedBuddy);

        restTemplate.delete(baseUrl + "/addressBook/" + book.getId() + "/buddies/" + savedBuddy.getName());

        ResponseEntity<BuddyInfo[]> getResponse = restTemplate.getForEntity(
                baseUrl + "/addressBook/" + book.getId() + "/buddies", BuddyInfo[].class);
        BuddyInfo[] buddies = getResponse.getBody();
        assertNotNull(buddies);
        assertEquals(0, buddies.length);
    }
}
