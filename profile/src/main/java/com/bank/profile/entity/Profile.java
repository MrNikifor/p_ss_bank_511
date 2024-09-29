package com.bank.profile.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
public class Profile {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @Size(max = 264)
    private String email;

    @Column(name = "name_on_card")
    @Size(max = 370)
    private String nameOnCard;

    @Column(unique = true)
    private Long inn;

    @Column(unique = true)
    private Long snils;

    @Column(name = "passport_id", nullable = false)
    private Long passportId;

    @Column(name = "actual_registration_id")
    private Long actualRegistrationId;

}
