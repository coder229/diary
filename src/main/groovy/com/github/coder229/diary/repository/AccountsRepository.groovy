package com.github.coder229.diary.repository

import com.github.coder229.diary.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by scott on 9/5/2015.
 */
interface AccountsRepository extends MongoRepository<Account, String> {

    Account findByUsername(String username)
}
