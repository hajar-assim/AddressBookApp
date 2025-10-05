package org.addressbookproject.controller;

import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.entity.BuddyInfo;
import org.addressbookproject.repository.AddressBookRepository;
import org.addressbookproject.repository.BuddyInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AddressBookController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookController(AddressBookRepository addressBookRepository,
                                 BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    // Get all address books
    @GetMapping("/addressBooks")
    public ResponseEntity<List<AddressBook>> getAllAddressBooks() {
        try {
            List<AddressBook> books = (List<AddressBook>) addressBookRepository.findAll();
            System.out.println("Retrieved all address books: " + books.size());
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            System.out.println("Failed to retrieve AddressBooks: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Create a new address book
    @PostMapping("/addressBook")
    public ResponseEntity<AddressBook> createAddressBook() {
        try {
            AddressBook saved = addressBookRepository.save(new AddressBook());
            System.out.println("Created new AddressBook with id: " + saved.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            System.out.println("Failed to create AddressBook: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieve all buddies in an address book
    @GetMapping("/addressBook/{addressBookId}/buddies")
    public ResponseEntity<List<BuddyInfo>> retrieveBuddies(@PathVariable Long addressBookId) {
        Optional<AddressBook> optional = addressBookRepository.findById(addressBookId);
        if (optional.isEmpty()) {
            System.out.println("AddressBook not found: " + addressBookId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<BuddyInfo> buddies = optional.get().getBuddies();
        System.out.println("Retrieved " + buddies.size() + " buddies from AddressBook " + addressBookId);
        return ResponseEntity.ok(buddies);
    }

    // Add a buddy to an address book
    @PostMapping("/addressBook/{addressBookId}/buddies")
    public ResponseEntity<BuddyInfo> addBuddy(@PathVariable Long addressBookId, @RequestBody BuddyInfo buddy) {
        Optional<AddressBook> optional = addressBookRepository.findById(addressBookId);
        if (optional.isEmpty()) {
            System.out.println("AddressBook not found: " + addressBookId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            BuddyInfo savedBuddy = buddyInfoRepository.save(buddy);
            AddressBook addressBook = optional.get();
            addressBook.addBuddy(savedBuddy);
            addressBookRepository.save(addressBook);
            System.out.println("Added buddy " + savedBuddy.getName() + savedBuddy.getPhoneNumber() + " to AddressBook " + addressBookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBuddy);
        } catch (Exception e) {
            System.out.println("Failed to add Buddy: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Remove a buddy from an address book
    @DeleteMapping("/addressBook/{addressBookId}/buddies/{buddyName}")
    public ResponseEntity<BuddyInfo> removeBuddy(@PathVariable Long addressBookId, @PathVariable String buddyName) {
        Optional<AddressBook> optionalAddressBook = addressBookRepository.findById(addressBookId);

        if (optionalAddressBook.isEmpty()) {
            System.out.println("AddressBook not found: " + addressBookId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            AddressBook addressBook = optionalAddressBook.get();
            BuddyInfo buddy = buddyInfoRepository.findByName(buddyName);
            boolean removed = addressBook.removeBuddyByName(buddy.getName());
            if (removed) {
                buddyInfoRepository.delete(buddy);
                addressBookRepository.save(addressBook);
                System.out.println("Removed buddy " + buddy.getName() + " from AddressBook " + addressBookId);
                return ResponseEntity.ok(buddy);
            } else {
                System.out.println("Buddy not found in AddressBook: " + buddy.getName());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.out.println("Failed to remove Buddy: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
