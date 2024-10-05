package com.bank.profile.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner
{

//    private final BaseCrudService<ProfileDTO> profileService;
//    private final BaseCrudService<RegistrationDTO> registrationService;
//    private final BaseCrudService<PassportDTO> passportService;
//
//    private final ProfileMapper profileMapper;
//    private final RegistrationMapper registrationMapper;
//    private final PassportMapper passportMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //createTestProfile(createTestPassport(createTestRegistration()));
    }

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
