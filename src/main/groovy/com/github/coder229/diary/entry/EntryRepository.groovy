package com.github.coder229.diary.entry

import org.springframework.data.mongodb.repository.MongoRepository

import java.time.LocalDate

/**
 * Created by scott on 9/2/2015.
 */
interface EntryRepository extends MongoRepository<Entry, String> {
    Entry findOneByDate(LocalDate date)
}
