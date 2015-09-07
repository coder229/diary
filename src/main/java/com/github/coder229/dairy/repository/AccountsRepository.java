package com.github.coder229.dairy.repository;

import com.github.coder229.dairy.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by scott on 9/5/2015.
 */
public interface AccountsRepository extends MongoRepository<Account, String> {

    Account findByUsername(String username);
}
