/*
 * (c) Copyright IBM Corp. 2026
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Profile entity representing a user profile stored in MongoDB.
 */
@Document(collection = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

  @Id
  private String id;

  private String name;

  private String employer;

  private String email;
}
