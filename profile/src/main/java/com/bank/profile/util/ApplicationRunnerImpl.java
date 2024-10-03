package com.bank.profile.util;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Profile;
import com.bank.profile.entity.Registration;
import com.bank.profile.mappers.exact.PassportMapper;
import com.bank.profile.mappers.exact.ProfileMapper;
import com.bank.profile.mappers.exact.RegistrationMapper;
import com.bank.profile.service.generics.CrudService;
import com.bank.profile.service.interfaces.PassportService;
import com.bank.profile.service.interfaces.ProfileService;
import com.bank.profile.service.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ApplicationRunnerImpl
        //implements ApplicationRunner
{

//    private final CrudService<ProfileDTO> profileService;
//    private final CrudService<RegistrationDTO> registrationService;
//    private final CrudService<PassportDTO> passportService;
//
//    private final ProfileMapper profileMapper;
//    private final RegistrationMapper registrationMapper;
//    private final PassportMapper passportMapper;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        RegistrationDTO registrationDTO = createTestRegistration();
//        PassportDTO passportDTO = createTestPassport(registrationDTO);
//        createTestProfile(passportDTO);
//    }
//
//    public ProfileDTO createTestProfile(PassportDTO passport){
//        Profile testProfile = new Profile();
//        testProfile.setPhoneNumber(8005553535L);
//        testProfile.setEmail("email@mail.ru");
//        testProfile.setNameOnCard("Tony Montana");
//        testProfile.setPassportId(passportMapper.toEntity(passport));
//        return profileService.create(profileMapper.toDto(testProfile));
//    }
//
//    public PassportDTO createTestPassport(RegistrationDTO registrationDTO){
//        Passport testPassport = new Passport();
//        testPassport.setNumber(123123123123123L);
//        testPassport.setLastName("PassportLastName");
//        testPassport.setFirstName("PassportFirstName");
//        testPassport.setGender(Passport.Gender.МУЖ);
//        testPassport.setBirthDate(LocalDate.now());
//        testPassport.setBirthPlace("Moscow");
//        testPassport.setIssuedBy("PassportIssuedBy");
//        testPassport.setDateOfIssue(LocalDate.now());
//        testPassport.setDivisionCode(999);
//        testPassport.setRegistrationId(registrationMapper.toEntity(registrationDTO));
//        return passportService.create(passportMapper.toDto(testPassport));
//    }
//
//    public RegistrationDTO createTestRegistration(){
//        Registration testRegistration = new Registration();
//        testRegistration.setCountry("Russia");
//        testRegistration.setRegion("Moscowskiy");
//        testRegistration.setIndex(66666L);
//        return registrationService.create(registrationMapper.toDto(testRegistration));
//    }
}
