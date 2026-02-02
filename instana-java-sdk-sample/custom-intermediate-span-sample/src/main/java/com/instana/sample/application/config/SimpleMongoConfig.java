/*
 * (c) Copyright IBM Corp. 2026
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.instana.sample.application")
public class SimpleMongoConfig {

  @Bean
  public MongoClient mongo() throws Exception {
    final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
    final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
    return MongoClients.create(mongoClientSettings);
  }

}
