package com.bank.profile.entity.exact;

import com.bank.profile.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(optional = false)
    @JoinColumn(name = "passport_id", nullable = false)
    private Passport passportId;

    @OneToOne
    @JoinColumn(name = "actual_registration_id")
//    @NotFound(action= NotFoundAction.IGNORE)
    private ActualRegistration actualRegistrationId;

}
