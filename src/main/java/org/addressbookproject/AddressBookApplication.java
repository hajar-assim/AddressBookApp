package org.addressbookproject;

import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.entity.BuddyInfo;
import org.addressbookproject.service.AddressBookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AddressBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AddressBookService service) {
        return args -> {
            AddressBook book = service.createAddressBook();
            service.addBuddy(book, "Aang", "123-456");
            service.addBuddy(book, "Zuko", "789-101");
            System.out.println("Buddies saved!");

            List<BuddyInfo> buddies = service.findAllBuddies();
            System.out.println("All my buddies are...");
            for (BuddyInfo buddy: buddies) {
                System.out.println("Buddy: " +  buddy.toString());
            }
        };
    }
}

