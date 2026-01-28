/*
 * (c) Copyright IBM Corp. 2026
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.application.controller;

import com.instana.sample.application.model.Profile;
import com.instana.sample.application.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/instana")
@Tag(name = "Profile Management", description = "APIs for managing user profiles with Instana SDK custom spans")
class InstanaJavaSdkController {

  private final ProfileService profileService;

  @GetMapping(value = "/profile", produces = "application/json")
  @Operation(summary = "Get all profiles", description = "Retrieves all profiles with optional error simulation for testing custom span error handling")
  public List<Profile> getAll(
      @Parameter(description = "Simulate error for testing span error handling", example = "false")
      @RequestParam(name = "error", defaultValue = "false") Boolean error) {
    return profileService.all(error);
  }

  @PostMapping(value = "/profile", produces = "application/json")
  @Operation(summary = "Create a new profile", description = "Creates a new user profile in the database")
  public Profile create(@RequestBody Profile profile) {
    return profileService.create(profile.getName(), profile.getEmployer(), profile.getEmail());
  }
}
