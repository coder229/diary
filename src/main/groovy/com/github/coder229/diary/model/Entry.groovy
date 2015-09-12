package com.github.coder229.diary.model

import org.springframework.data.annotation.Id

import javax.xml.bind.annotation.XmlRootElement

/**
 * Created by scott on 9/2/2015.
 */
@XmlRootElement
class Entry {

    @Id
    String id;
    String userId;
    Date date;
    String notes;
}
