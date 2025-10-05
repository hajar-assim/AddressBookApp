package org.addressbookproject.service;

import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.entity.BuddyInfo;
import org.addressbookproject.repository.AddressBookRepository;
import org.addressbookproject.repository.BuddyInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Autowired
    private BuddyInfoRepository buddyInfoRepo;

    public AddressBook createAddressBook() {
        AddressBook book = new AddressBook();
        return addressBookRepo.save(book);
    }

    public BuddyInfo addBuddy(AddressBook book, String name, String phone) {
        BuddyInfo buddy = new BuddyInfo(name, phone);
        book.getBuddies().add(buddy);
        buddyInfoRepo.save(buddy);
        return buddy;
    }
    public BuddyInfo addBuddy(AddressBook book, String name, String phone, String address) {
        BuddyInfo buddy = new BuddyInfo(name, phone, address);
        book.getBuddies().add(buddy);
        buddyInfoRepo.save(buddy);
        return buddy;
    }

    public List<BuddyInfo> findAllBuddies() {
        return (List<BuddyInfo>)buddyInfoRepo.findAll();
    }
}
