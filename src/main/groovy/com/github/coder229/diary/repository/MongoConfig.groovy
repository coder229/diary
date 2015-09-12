/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.coder229.diary.repository

import com.mongodb.MongoClient
import com.mongodb.WriteConcern
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 *
 * @author scott
 */
@Configuration
@EnableMongoRepositories()
@ComponentScan()
class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        "diary"
    }

    @Override
    @Bean
    MongoClient mongo() throws Exception {
        MongoClient client = new MongoClient("glados")
        client.setWriteConcern(WriteConcern.SAFE)
        client
    }

    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        new MongoTemplate(mongo(), getDatabaseName())
    }
}
