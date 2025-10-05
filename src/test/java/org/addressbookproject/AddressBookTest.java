//package org.addressbookproject;
//
//import org.addressbookproject.entity.AddressBook;
//import org.addressbookproject.entity.BuddyInfo;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class AddressBookTest {
//
//    private AddressBook addressBook;
//
//    @Before
//    public void setUp() throws Exception {
//        addressBook = new AddressBook();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        addressBook = null;
//    }
//
//    @Test
//    public void addBuddy() {
//        BuddyInfo buddy = new BuddyInfo("Aang", "12345");
//        addressBook.addBuddy(buddy);
//        assertEquals(1, addressBook.getBuddies().size());
//        assertTrue(addressBook.getBuddies().contains(buddy));
//    }
//
//    @Test
//    public void removeBuddyByName() {
//        BuddyInfo buddy = new BuddyInfo("Katara", "67890");
//        addressBook.addBuddy(buddy);
//        assertTrue(addressBook.removeBuddyByName("Katara"));
//        assertEquals(0, addressBook.getBuddies().size());
//        assertFalse(addressBook.removeBuddyByName("Toph"));
//    }
//
//    @Test
//    public void getBuddies() {
//        BuddyInfo b1 = new BuddyInfo("Sokka", "111");
//        BuddyInfo b2 = new BuddyInfo("Zuko", "222");
//        addressBook.addBuddy(b1);
//        addressBook.addBuddy(b2);
//        List<BuddyInfo> buddies = addressBook.getBuddies();
//        assertEquals(2, buddies.size());
//        assertTrue(buddies.contains(b1));
//        assertTrue(buddies.contains(b2));
//    }
//
//    @Test
//    public void testToString() {
//        BuddyInfo buddy = new BuddyInfo("Iroh", "333");
//        addressBook.addBuddy(buddy);
//        String result = addressBook.toString();
//        assertTrue(result.contains("Iroh"));
//        assertTrue(result.contains("333"));
//    }
//}
