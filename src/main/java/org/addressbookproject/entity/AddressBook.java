package org.addressbookproject.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "addressbook_id")
    private List<BuddyInfo> buddies = new ArrayList<>();

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            buddies.add(buddy);
        }
    }

    public boolean removeBuddyByName(String name) {
        for (int i = 0; i < buddies.size(); i++) {
            BuddyInfo b = buddies.get(i);
            if (b.getName() != null && b.getName().equals(name)) {
                buddies.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AddressBook{buddies=" + buddies + "}";
    }
}

