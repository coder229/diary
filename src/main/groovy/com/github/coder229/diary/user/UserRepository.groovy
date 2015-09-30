package com.github.coder229.diary.user

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by scott on 9/5/2015.
 */
interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username)
}
