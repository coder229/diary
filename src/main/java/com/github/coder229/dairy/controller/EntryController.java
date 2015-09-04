package com.github.coder229.dairy.controller;

import com.github.coder229.dairy.model.Entry;
import com.github.coder229.dairy.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by scott on 9/2/2015.
 */
@RestController
@RequestMapping(value="/api/entries")
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> list() {
        return entryRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Entry create(@RequestBody Entry entry) {
        return entryRepository.save(entry);
    }
}
