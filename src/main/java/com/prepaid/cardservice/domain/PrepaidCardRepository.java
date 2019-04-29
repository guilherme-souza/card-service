package com.prepaid.cardservice.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrepaidCardRepository extends MongoRepository<PrepaidCard, Long> {

}
