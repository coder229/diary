package com.github.coder229.diary.controller

import com.github.coder229.diary.model.Entry
import com.github.coder229.diary.repository.EntriesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by scott on 9/2/2015.
 */
@RestController
@RequestMapping(value="/api/entries")
class EntryController {

    @Autowired
    EntriesRepository entryRepository;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<Entry> list() {
        return entryRepository.findAll()
    }

    @RequestMapping(value="/{entryId}", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> get(@PathVariable String entryId) {
        Entry entry = entryRepository.findOne(entryId)

        if (entry != null) {
            return new ResponseEntity<>(entry, HttpStatus.OK)
        }

        new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND)
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Entry create(@RequestBody Entry entry) {
        entryRepository.save(entry)
    }

    @RequestMapping(value="/{entryId}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable String entryId) {
        entryRepository.delete(entryId)
    }
}
