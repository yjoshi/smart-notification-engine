package com.walmart.hackathon.sne.repository;

import com.walmart.hackathon.sne.entity.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingWithSneRepository extends CrudRepository<UserMappingWithSne, Integer> {

}
