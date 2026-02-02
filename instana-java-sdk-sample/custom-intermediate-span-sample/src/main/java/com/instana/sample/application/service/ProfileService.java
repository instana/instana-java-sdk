/*
 * (c) Copyright IBM Corp. 2026
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.application.service;

import com.instana.sample.application.model.Profile;
import com.instana.sample.application.repository.ProfileRepository;
import com.instana.sdk.annotation.Span;
import com.instana.sdk.support.SpanSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
  private static final String SPAN_NAME = "custom-instana-java-sdk";
  private static final String TAG_SDK = "tags.instana.java.sdk";
  private static final String TAG_ERROR = "tags.error";
  
  private final ProfileRepository profileRepository;

  /**
   * Retrieves all profiles from the database with custom Instana span tracking.
   * Demonstrates intermediate span creation and error handling with custom tags.
   */
  @Span(type = Span.Type.INTERMEDIATE, value = SPAN_NAME)
  public List<Profile> all(Boolean error) {
    try {
      if (Boolean.TRUE.equals(error)) {
        log.warn("Simulating error for span testing");
        throw new RuntimeException("Simulated error for testing custom span error handling");
      }
      
      List<Profile> profiles = profileRepository.findAll();
      SpanSupport.annotate(Span.Type.INTERMEDIATE, SPAN_NAME, TAG_SDK, "Success");
      SpanSupport.annotate(Span.Type.INTERMEDIATE, SPAN_NAME, "tags.profile.count", String.valueOf(profiles.size()));
      log.info("Successfully retrieved {} profiles", profiles.size());
      
      return profiles;
    } catch (Exception e) {
      log.error("Error retrieving profiles: {}", e.getMessage(), e);
      SpanSupport.annotate(Span.Type.INTERMEDIATE, SPAN_NAME, TAG_ERROR, "true");
      SpanSupport.annotate(Span.Type.INTERMEDIATE, SPAN_NAME, TAG_SDK, "Fail");
      SpanSupport.annotate(Span.Type.INTERMEDIATE, SPAN_NAME, "tags.error.message", e.getMessage());
      return Collections.emptyList();
    }
  }

  /**
   * Creates a new profile in the database.
   */
  public Profile create(String name, String employer, String email) {
    log.info("Creating new profile for: {}", name);
    
    Profile profile = new Profile();
    profile.setName(name);
    profile.setEmployer(employer);
    profile.setEmail(email);
    
    Profile savedProfile = profileRepository.save(profile);
    log.info("Profile created with ID: {}", savedProfile.getId());
    
    return savedProfile;
  }
}
