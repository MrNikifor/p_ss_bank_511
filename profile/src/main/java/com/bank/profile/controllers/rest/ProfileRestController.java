package com.bank.profile.controllers.rest;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.Profile;
import com.bank.profile.service.generics.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@RestController
@MultipartConfig(maxFileSize = 1024 * 1024, maxRequestSize = 1024 * 1024)
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileRestController {

    private final CrudService<ProfileDTO> profileService;
    private final CrudService<RegistrationDTO> registrationService;
    private final CrudService<PassportDTO> passportService;

    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileDTO>> showAllProfiles() {
        return new ResponseEntity<>(profileService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<RegistrationDTO>> showAllRegistrations() {
        return new ResponseEntity<>(registrationService.readAll(), HttpStatus.OK);
    }

    @PostMapping("/registration/new")
    public ResponseEntity<HttpStatus> create(@RequestBody RegistrationDTO registrationDTO) {
        registrationService.create(registrationDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
