package org.addressbookproject;

import org.addressbookproject.entity.BuddyInfo;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    @Test
    public void getName() {
        BuddyInfo buddy = new BuddyInfo("Aang", "12345");
        assertEquals("Aang", buddy.getName());
    }

    @Test
    public void setName() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Katara");
        assertEquals("Katara", buddy.getName());
    }

    @Test
    public void getPhoneNumber() {
        BuddyInfo buddy = new BuddyInfo("Sokka", "67890");
        assertEquals("67890", buddy.getPhoneNumber());
    }

    @Test
    public void setPhoneNumber() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setPhoneNumber("99999");
        assertEquals("99999", buddy.getPhoneNumber());
    }

    @Test
    public void testToString() {
        BuddyInfo buddy = new BuddyInfo("Zuko", "11111");
        String result = buddy.toString();
        assertTrue(result.contains("Zuko"));
        assertTrue(result.contains("11111"));
    }
}
