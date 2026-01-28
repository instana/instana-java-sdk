/*
 * (c) Copyright IBM Corp. 2026
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.application.repository;

import com.instana.sample.application.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
}