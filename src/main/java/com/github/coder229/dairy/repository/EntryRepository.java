package com.github.coder229.dairy.repository;

import com.github.coder229.dairy.model.Entry;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by scott on 9/2/2015.
 */
@Document(collection="entries")
public interface EntryRepository extends MongoRepository<Entry, String> {

}
