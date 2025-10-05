package org.addressbookproject.repository;

import org.addressbookproject.entity.BuddyInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long>{
    BuddyInfo findByName(String name);

    void deleteAll();
}
