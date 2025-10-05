package org.addressbookproject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.entity.BuddyInfo;

import java.util.List;

public class JPATest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        BuddyInfo b1 = new BuddyInfo("Alice", "111-222");
        BuddyInfo b2 = new BuddyInfo("Bob", "333-444");

        AddressBook ab = new AddressBook();
        ab.addBuddy(b1);
        ab.addBuddy(b2);

        em.persist(ab);

        em.getTransaction().commit();

        List<BuddyInfo> buddies = em.createQuery("SELECT b FROM BuddyInfo b", BuddyInfo.class).getResultList();
        System.out.println("Buddies in DB: " + buddies.size());

        em.close();
        emf.close();
    }
}