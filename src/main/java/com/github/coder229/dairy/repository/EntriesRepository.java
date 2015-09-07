package com.github.coder229.dairy.repository;

import com.github.coder229.dairy.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

/**
 * Created by scott on 9/2/2015.
 */
public interface EntriesRepository extends MongoRepository<Entry, String> {

    Entry findOneByDate(LocalDate date);
}
