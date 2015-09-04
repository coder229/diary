package com.github.coder229.dairy.model;

import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by scott on 9/2/2015.
 */
@XmlRootElement
public class Entry {

    @Id
    private String id;
    private LocalDate date;
    private String notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
