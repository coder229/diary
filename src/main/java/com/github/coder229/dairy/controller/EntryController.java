package com.github.coder229.dairy.controller;

import com.github.coder229.dairy.model.Entry;
import com.github.coder229.dairy.repository.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by scott on 9/2/2015.
 */
@RestController
@RequestMapping(value="/api/entries")
public class EntryController {

    @Autowired
    private EntriesRepository entryRepository;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> list() {
        return entryRepository.findAll();
    }

    @RequestMapping(value="/{entryId}", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get(@PathVariable String entryId) {
        Entry entry = entryRepository.findOne(entryId);

        if (entry != null) {
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }

        return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Entry create(@RequestBody Entry entry) {
        return entryRepository.save(entry);
    }

    @RequestMapping(value="/{entryId}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String entryId) {
        entryRepository.delete(entryId);
    }
}
