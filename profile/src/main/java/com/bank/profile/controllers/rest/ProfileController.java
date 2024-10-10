package com.bank.profile.controllers.rest;

import com.bank.profile.controllers.rest.generics.AbstractController;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.entity.Profile;
import com.bank.profile.mappers.ProfileMapper;
import com.bank.profile.service.ProfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController extends AbstractController<Profile, ProfileDTO, ProfileService, ProfileMapper> {

    public ProfileController(ProfileService service, ProfileMapper mapper, ProfileDTO profileDTO) {
        super(service, mapper, profileDTO);
    }
}
