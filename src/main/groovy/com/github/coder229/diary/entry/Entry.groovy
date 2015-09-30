package com.github.coder229.diary.entry
import org.springframework.data.annotation.Id
/**
 * Created by scott on 9/2/2015.
 */
class Entry {
    @Id
    String id
    String userId
    Date date
    String notes
}
